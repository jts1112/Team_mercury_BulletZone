package edu.unh.cs.cs619.bulletzone.control;

import android.view.View;

import edu.unh.cs.cs619.bulletzone.ClientActivity;
import edu.unh.cs.cs619.bulletzone.R;
import edu.unh.cs.cs619.bulletzone.ui.GridModel;

import java.util.Timer;
import java.util.TimerTask;


public class ControlUpdater {
    private Timer controlUpdateTimer;
    private final long CONTROL_UPDATE_INTERVAL = 500;

    private final ClientActivity clientActivity;
    private GridModel grid;

    public ControlUpdater(ClientActivity clientActivity, GridModel grid) {
        this.clientActivity = clientActivity;
        this.grid = grid;
    }

    public void startControlUpdateTimer() {
        controlUpdateTimer = new Timer();
        controlUpdateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                clientActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateControls();
                    }
                });
            }
        }, 0, CONTROL_UPDATE_INTERVAL);
    }

    public void stopControlUpdateTimer() {
        if (controlUpdateTimer != null) {
            controlUpdateTimer.cancel();
            controlUpdateTimer = null;
        }
    }

    private void updateControls() {
        ControlState controlState = clientActivity.getControlState();
        if (controlState != null) {
            controlState.updateControls(grid.getSurroundingCells());
        }
    }
}
