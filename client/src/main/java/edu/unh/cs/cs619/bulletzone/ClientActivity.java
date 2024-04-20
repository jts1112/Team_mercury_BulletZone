package edu.unh.cs.cs619.bulletzone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import edu.unh.cs.cs619.bulletzone.events.GameData;
import edu.unh.cs.cs619.bulletzone.events.GameDataObserver;
import edu.unh.cs.cs619.bulletzone.events.GameEventProcessor;
import edu.unh.cs.cs619.bulletzone.replay.GameReplayManager;
import edu.unh.cs.cs619.bulletzone.rest.GridPollerTask;
import edu.unh.cs.cs619.bulletzone.ui.GridAdapter;
import edu.unh.cs.cs619.bulletzone.ui.GridEventHandler;
import edu.unh.cs.cs619.bulletzone.ui.GridModel;

@SuppressLint("NonConstantResourceId")
@EActivity(R.layout.activity_client)
public class ClientActivity extends Activity implements GameDataObserver {

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
    protected GameData gameData;
    protected GameReplayManager replay;

    /**
     * Changed unitIds to a singleton used in actioncontroller
     * and imagemapper to distinguish between player and enemy units
     */
    private int tappedX = -1;
    private int tappedY = -1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        gridModel = new GridModel();
        mGridAdapter = new GridAdapter(this);

        gameData = GameData.getInstance();
        replay = GameReplayManager.getInstance(this);
        replay.startRecording();

        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(mGridAdapter);

        // Initialize gridEventHandler after gridModel initialization
        gridEventHandler = new GridEventHandler(gridModel, mGridAdapter);
        gameData.registerObserver(this);

        // Automatically activate eventSwitch after a delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onEventSwitch();
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gridEventHandler.unregister();
        gameData.unregisterObserver(this);
        replay.endRecording();
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
            onPlayerCreditUpdate(gameData.getPlayerCredits());
        } catch (Exception ignored) { }
    }

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
    }

    @Click(R.id.buttonMine)
    @Background
    protected void onButtonMine() {
        actionController.onButtonMine();
    }

    @Click(R.id.buttonEject)
    @Background
    protected void onButtonEjectPowerUp() {
        System.out.println("Client ejecting power-up button pressed");
        actionController.onButtonEjectPowerUp();
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
        System.out.println("Updating controls for unit: " + unit);
        Button buttonEjectPowerUp = findViewById(R.id.buttonEject);
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
            buttonEjectPowerUp.setVisibility(View.VISIBLE);
        } else if ("dropship".equals(unit)) {
//            buttonFire.setVisibility(View.GONE);  // Dropships can fire.
            buttonUp.setVisibility(View.VISIBLE);
            buttonDown.setVisibility(View.VISIBLE);
            buttonLeft.setVisibility(View.VISIBLE);
            buttonRight.setVisibility(View.VISIBLE);
            buttonFire.setVisibility(View.VISIBLE);
            buttonEjectPowerUp.setVisibility(View.VISIBLE);
        } else { // Tank
            buttonUp.setVisibility(View.VISIBLE);
            buttonDown.setVisibility(View.VISIBLE);
            buttonLeft.setVisibility(View.VISIBLE);
            buttonRight.setVisibility(View.VISIBLE);
            buttonFire.setVisibility(View.VISIBLE);
            buttonEjectPowerUp.setVisibility(View.VISIBLE);
        }
    }

    // Method to update tank life value in TextView
    @Override
    public void onTankLifeUpdate(long life) {
        runOnUiThread(() -> {
            TextView tankLife = findViewById(R.id.textViewTankLife);
            tankLife.setText("Tank Armor: " + life);
        });
    }

    // Method to update miner life value in TextView
    @Override
    public void onMinerLifeUpdate(long life) {
        runOnUiThread(() -> {
            TextView minerLife = findViewById(R.id.textViewMinerLife);
            minerLife.setText("Miner Armor: " + life);
        });
    }

    // Method to update dropship life value in TextView
    @Override
    public void onDropshipLifeUpdate(long life) {
        runOnUiThread(() -> {
            TextView dropshipLife = findViewById(R.id.textViewDropshipLife);
            dropshipLife.setText("Dropship Armor: " + life);
        });
    }

    @Override
    public void onPlayerCreditUpdate(long creditVal) {
        runOnUiThread(() -> {
            TextView dropshipLife = findViewById(R.id.textCreditsView);
            dropshipLife.setText("Credits: " + creditVal);
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Click(R.id.buttonLeave)
    @Background
    void leaveGame() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long playerCredits = gameData.getPlayerCredits();
                if (playerCredits >= 1000) {
                    new AlertDialog.Builder(ClientActivity.this)
                            .setTitle("Leave Game")
                            .setMessage("Confirm to spend 1000 credits to leave.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Player clicked yes. Leave game
                                    System.out.println("leaveGame() called");

                                    // minus 1000 credits from the player
                                    gameData.addPlayerCredits(-1000);
                                    leaveAsync();
                                    Intent intent = new Intent(ClientActivity.this, TitleScreenActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null) // do nothing if user clicks no
                            .show();
                } else {
                    new AlertDialog.Builder(ClientActivity.this)
                            .setTitle("Leave Game")
                            .setMessage("You do not have enough credits to leave.")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
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

    @AfterViews
    protected void afterViewInjection() {
        joinAsync();
        SystemClock.sleep(500);
        gridView.setAdapter(mGridAdapter);
    }

    public void onGridItemTapped(int tappedX, int tappedY) {
        this.tappedX = tappedX;
        this.tappedY = tappedY;
        System.out.println("GridItemTapped at: " + tappedX + ", " + tappedY);
        showMoveToButton();
    }

    private void showMoveToButton() {
        // Shows the "Move To" button
        Button moveToButton = findViewById(R.id.buttonMoveTo);
        moveToButton.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NonConstantResourceId")
    @Click(R.id.buttonMoveTo)
    protected void onMoveToButtonClick() {
        EditText editTextRow = findViewById(R.id.editTextRow);
        EditText editTextColumn = findViewById(R.id.editTextColumn);

        String rowString = editTextRow.getText().toString().trim();
        String columnString = editTextColumn.getText().toString().trim();

        if (!rowString.isEmpty() && !columnString.isEmpty()) {
            int row = Integer.parseInt(rowString);
            int column = Integer.parseInt(columnString);

            // Perform the move action to the target position
            actionController.moveToPosition(column, row);

            // Clear the text boxes
            editTextRow.setText("");
            editTextColumn.setText("");
        }
    }

    public void clickEvent(View view) {

    }
}
