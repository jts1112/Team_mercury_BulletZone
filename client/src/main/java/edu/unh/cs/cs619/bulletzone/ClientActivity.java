package edu.unh.cs.cs619.bulletzone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import edu.unh.cs.cs619.bulletzone.events.GameEventProcessor;
import edu.unh.cs.cs619.bulletzone.rest.GridPollerTask;
import edu.unh.cs.cs619.bulletzone.ui.GridAdapter;
import edu.unh.cs.cs619.bulletzone.ui.GridEventHandler;
import edu.unh.cs.cs619.bulletzone.ui.GridModel;
@SuppressLint("NonConstantResourceId")
@EActivity(R.layout.activity_client)
public class ClientActivity extends Activity {

//    private static final String TAG = "ClientActivity";
    @Bean
    protected GridAdapter mGridAdapter;
    @Bean
    protected GameEventProcessor eventProcessor;
    protected GridEventHandler gridEventHandler;
    @ViewById
    protected GridView gridView;
    @NonConfigurationInstance
    @Bean
    GridPollerTask gridPollTask;
    @Bean
    protected ActionController actionController;  // Add new controller for rest calls
    private GridModel gridModel;
    @ViewById
    protected ProgressBar healthBar;
    /**
     * Remote tank identifier
     */

    /**
     * Removed all unit ids in client activity to be used in actioncontroller
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        gridModel = new GridModel();
        mGridAdapter = new GridAdapter(this);

        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(mGridAdapter);

        // Initialize gridEventHandler after gridModel initialization
        gridEventHandler = new GridEventHandler(gridModel, mGridAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gridEventHandler.unregister();
    }

    /**
     * Otto has a limitation (as per design) that it will only find methods on the immediate
     * class type. As a result, if at runtime this instance actually points to a subclass
     * implementation, the methods registered in this class will not be found. This immediately
     * becomes a problem when using the AndroidAnnotations framework as it always produces a
     * subclass of annotated classes.
     * <p>
     * To get around the class hierarchy limitation, one can use a separate anonymous class to
     * handle the events.

    private Object gridEventHandler = new Object()
    {
        @Subscribe
        public void onUpdateGrid(GridUpdateEvent event) {
            updateGrid(event.gw);
        }
    };
    */

    @AfterViews
    protected void afterViewInjection() {
        joinAsync();
        SystemClock.sleep(500);
        gridView.setAdapter(mGridAdapter);
    }

    @AfterInject
    void afterInject() {
        // initialize actioncontroller
        actionController.initialize(this);
    }

    @Background
    void joinAsync() {
        try {
            actionController.join();
            gridPollTask.startPolling();
        } catch (Exception ignored) { }
    }

//    public void updateGrid(GridWrapper gw) {
//        mGridAdapter.updateList(gw.getGrid());
//    }

    @SuppressLint("NonConstantResourceId")
    @Click (R.id.eventSwitch)
    protected void onEventSwitch() {
        if (gridPollTask.toggleEventUsage()) {
            Log.d("EventSwitch", "ON");
            eventProcessor.setBoard(gridModel.getRawGrid()); //necessary because "board" keeps changing when it's int[][]
            eventProcessor.start();
        } else {
            Log.d("EventSwitch", "OFF");
            eventProcessor.stop();
        }
    }

    /**
     * Client side only sends a move request whenever direction is pressed
     * Server determines whether to turn or move based on the tank direction
     */
    @Click({R.id.buttonUp, R.id.buttonDown, R.id.buttonLeft, R.id.buttonRight})
    protected void onButtonMove(View view) {
        final int viewId = view.getId();

        actionController.onButtonMove(viewId);
    }

    @SuppressLint("NonConstantResourceId")
    @Click(R.id.buttonFire)
    @Background
    protected void onButtonFire() {
        actionController.onButtonFire();
        updateHealthBar();
    }

    @SuppressLint("NonConstantResourceId")
    @Click(R.id.buttonTank)
    protected void onTankButtonClick() {
        actionController.updateCurrentUnit("tank");
        updateControlsForUnit("tank");
    }

    @SuppressLint("NonConstantResourceId")
    @Click(R.id.buttonMiner)
    protected void onMinerButtonClick() {
        actionController.updateCurrentUnit("miner");
        updateControlsForUnit("miner");
    }

    @SuppressLint("NonConstantResourceId")
    @Click(R.id.buttonDropship)
    protected void onDropshipButtonClick() {
        actionController.updateCurrentUnit("dropship");
        updateControlsForUnit("dropship");
    }

    private void updateControlsForUnit(String unit) {
        Button buttonMine = findViewById(R.id.buttonMine);
        Button buttonUp = findViewById(R.id.buttonUp);
        Button buttonDown = findViewById(R.id.buttonDown);
        Button buttonLeft = findViewById(R.id.buttonLeft);
        Button buttonRight = findViewById(R.id.buttonRight);
        Button buttonFire = findViewById(R.id.buttonFire);

        buttonMine.setVisibility(View.GONE);  // Hide mine button first

        // Show buttons based on the selected unit
        if ("miner".equals(unit)) {
            buttonMine.setVisibility(View.VISIBLE);
        } else if ("dropship".equals(unit)) {
//            buttonFire.setVisibility(View.GONE);  // Dropships can fire.
            buttonUp.setVisibility(View.VISIBLE);
            buttonDown.setVisibility(View.VISIBLE);
            buttonLeft.setVisibility(View.VISIBLE);
            buttonRight.setVisibility(View.VISIBLE);
            buttonFire.setVisibility(View.VISIBLE);
        } else { // Tank
            buttonUp.setVisibility(View.VISIBLE);
            buttonDown.setVisibility(View.VISIBLE);
            buttonLeft.setVisibility(View.VISIBLE);
            buttonRight.setVisibility(View.VISIBLE);
            buttonFire.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Click(R.id.buttonLeave)
    @Background
    void leaveGame() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(ClientActivity.this)
                        .setTitle("Leave Game")
                        .setMessage("Are you sure you want to leave?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Player clicked yes. Leave game
                                System.out.println("leaveGame() called");

                                Intent intent = new Intent(ClientActivity.this, TitleScreenActivity.class);
                                startActivity(intent);

//                                BackgroundExecutor.cancelAll("grid_poller_task", true);
//                                actionController.leave(tankId);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null) // do nothing if user clicks no
                        .show();
            }
        });
    }

    @Background
    void leaveAsync() {
        System.out.println("Leave called, leaving game");
        BackgroundExecutor.cancelAll("grid_poller_task", true);
        actionController.leave();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        // Not calling **super**, disables back button in current screen.
    }

    public static void updateHealthBar() {
    // Firing and taking damage needs to work before we can test this.
    }
}
