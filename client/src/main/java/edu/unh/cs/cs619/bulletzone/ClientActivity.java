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
import android.widget.Button;
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
import java.text.MessageFormat;

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
    protected ActionController actionController;
    private GridModel gridModel;
    protected GameData gameData;
    protected GameReplayManager replay;
    // unitIds are a singleton in actionController + imageMapper to distinguish player / enemy units

    // ---------------------------------- Initialization ----------------------------------
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        gridModel = new GridModel();
        mGridAdapter = new GridAdapter(this);

        gameData = GameData.getInstance();
//        replay = GameReplayManager.getInstance(this);
//        replay.startRecording();

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
//        replay.endRecording();
    }

    @Background
    void joinAsync() {
        try {
            actionController.join();
            gridPollTask.startPolling();
            onPlayerCreditUpdate(gameData.getPlayerCredits());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------- Top Row Buttons ----------------------------------

    @Click(R.id.buttonTank)
    protected void onTankButtonClick() {
        actionController.updateCurrentUnit("tank");
        updateControlsForUnit("tank");
    }

    @Click(R.id.buttonMiner)
    protected void onMinerButtonClick() {
        actionController.updateCurrentUnit("miner");
        updateControlsForUnit("miner");
    }

    @Click(R.id.buttonDropship)
    protected void onDropshipButtonClick() {
        actionController.updateCurrentUnit("dropship");
        updateControlsForUnit("dropship");
    }

    @Click(R.id.buttonFlag)
    protected void onButtonFlag(){
        mGridAdapter.setFlagplaced(mGridAdapter.getSelectedPosition());
        mGridAdapter.notifyDataSetChanged();

        // Schedule the removal of the flag after 30 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mGridAdapter.setFlagplaced(-1); // set flag bool to be removed.
                mGridAdapter.notifyDataSetChanged();
            }
        }, 30000); // 30 seconds
    }

    // ---------------------------------- 2nd Row Buttons ----------------------------------

    @Click(R.id.buttonSpawnTank)
    protected void onSpawnTankButtonClick() {
        long playerCredits = gameData.getPlayerCredits();
        if (playerCredits >= 1000) {
            actionController.spawnUnit("tank");
            updateControlsForUnit("tank");
            gameData.addPlayerCredits(-1000);
        } else {
            showInsufficientCreditsDialog();
        }
    }

    @Click(R.id.buttonSpawnMiner)
    protected void onSpawnMinerButtonClick() {
        actionController.updateCurrentUnit("miner");
        updateControlsForUnit("miner");
    }

    // ---------------------------------- Move Buttons ----------------------------------

    /**
     * Client side only sends a move request whenever direction is pressed
     * Server determines whether to turn or move based on the tank direction
     */
    @Click({R.id.buttonUp, R.id.buttonDown, R.id.buttonLeft, R.id.buttonRight})
    protected void onButtonMove(View view) {
        final int viewId = view.getId();
        byte direction;

        switch (viewId) {
            case R.id.buttonUp:
                direction = 0;
                break;
            case R.id.buttonDown:
                direction = 4;
                break;
            case R.id.buttonLeft:
                direction = 6;
                break;
            case R.id.buttonRight:
                direction = 2;
                break;
            default:
                System.out.println("Invalid direction");
                direction = 0;
        }
        actionController.onButtonMove(direction);
    }

    @Click({R.id.buttonEvadeLeft, R.id.buttonEvadeRight})
    protected void onButtonEvade(View view) {
        final int viewId = view.getId();

        switch (viewId) {
            case R.id.buttonEvadeLeft:
                actionController.onButtonMove((byte) 6);
                break;
            case R.id.buttonEvadeRight:
                actionController.onButtonMove((byte) 2);
                break;
            default:
                break;
        }
    }

    // ---------------------------------- 2nd to Last Row Buttons ----------------------------------

    @Click(R.id.buttonTunnel)
    @Background
    protected void onButtonTunnel() {
        actionController.onButtonTunnel();
    }

    @Click(R.id.buttonFire)
    @Background
    protected void onButtonFire() {
        actionController.onButtonFire();
    }

    @Click(R.id.buttonMoveTo)
    protected void onMoveToButtonClick() {
        int selectedPosition = mGridAdapter.getSelectedPosition();
        if (selectedPosition >= 0) {
            int row = selectedPosition / 16;
            int col = selectedPosition % 16;
            actionController.moveToPosition(col, row);

            Log.d("Flag", "Placed flag at " + row + ", " + col);
        } else {
            Log.d("Flag", "No grid cell selected.");
        }
    }

    @Click(R.id.buttonMine)
    @Background
    protected void onButtonMine() {
        actionController.onButtonMine();
    }

    // ---------------------------------- Bottom Row Buttons ----------------------------------

    @Click (R.id.eventSwitch)
    protected void onEventSwitch() {
        if (gridPollTask.toggleEventUsage()) {
            Log.d("EventSwitch", "ON");
            gridEventHandler.setBoard3d(gridModel.getGrid3d()); //necessary because "board" keeps changing when it's int[][]
        } else {
            Log.d("EventSwitch", "OFF");
            gridEventHandler.stop();
        }
    }

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

                                    // minus 1000 credits
                                    gameData.addPlayerCredits(-1000);
                                    actionController.leaveAsync();
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

    @Click(R.id.buttonEject)
    @Background
    protected void onButtonEjectPowerUp() {
        System.out.println("Client ejecting power-up button pressed");
        actionController.onButtonEjectPowerUp();
    }

    // ---------------------------------- Helper Methods ----------------------------------

    @Override
    public void onTankLifeUpdate(long life) {
        runOnUiThread(() -> {
            TextView tankLife = findViewById(R.id.textViewTankLife);
            tankLife.setText(MessageFormat.format("Tank Armor: {0}", life));
        });
    }

    @Override
    public void onMinerLifeUpdate(long life) {
        runOnUiThread(() -> {
            TextView minerLife = findViewById(R.id.textViewMinerLife);
            minerLife.setText(MessageFormat.format("Miner Armor: {0}", life));
        });
    }

    @Override
    public void onDropshipLifeUpdate(long life) {
        runOnUiThread(() -> {
            TextView dropshipLife = findViewById(R.id.textViewDropshipLife);
            dropshipLife.setText(MessageFormat.format("Dropship Armor: {0}", life));
        });
    }

    @Override
    public void onPlayerCreditUpdate(long creditVal) {
        runOnUiThread(() -> {
            TextView dropshipLife = findViewById(R.id.textCreditsView);
            dropshipLife.setText(MessageFormat.format("Credits: {0}", creditVal));
        });
    }

    @Override
    public void onBackPressed() {}

    @AfterViews
    protected void afterViewInjection() {
        joinAsync();
        SystemClock.sleep(500);
        gridView.setAdapter(mGridAdapter);
    }

    public void showMoveToButton() {
        Button moveToButton = findViewById(R.id.buttonMoveTo);
        moveToButton.setVisibility(View.VISIBLE);
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

    private void showInsufficientCreditsDialog() {
        new AlertDialog.Builder(ClientActivity.this)
                .setTitle("Insufficient Credits")
                .setMessage("You need at least 1000 credits to spawn a unit.")
                .setPositiveButton(android.R.string.ok, null)
                .show();
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
}
