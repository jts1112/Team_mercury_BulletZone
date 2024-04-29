package edu.unh.cs.cs619.bulletzone.ui;

import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import edu.unh.cs.cs619.bulletzone.events.GameEvent;
import edu.unh.cs.cs619.bulletzone.replay.GameReplayManager;
import edu.unh.cs.cs619.bulletzone.rest.GridUpdateEvent;


public class GridEventHandler {

    private GridModel gridModel;
    private GridAdapter gridAdapter;
    private GameReplayManager replayManager;
    private int[][] board;
    public void setBoard(int[][] newBoard) { board = newBoard; }

    public void start() {
        EventBus.getDefault().register(this);
    }

    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onNewEvent(GameEvent event) {
        //Log.d("GameEventProcessor", "Applying " + event);
        event.applyTo(board);
    }

    public GridEventHandler(GridModel gridModel, GridAdapter gridAdapter) {
        this.gridModel = gridModel;
        this.gridAdapter = gridAdapter;
        replayManager = GameReplayManager.getInstance();
        EventBus.getDefault().register(this); // Register with EventBus
    }

    @Subscribe
    public void onUpdateGrid(GridUpdateEvent event) {
        int[][][] gridData = event.gw.getGrid3d();
        int[][][] terrainData = event.gw.getTerrainGrid3d();

        if (gridModel != null) {
            gridModel.updateGrid3d(gridData, terrainData);
            replayManager.takeSnapshot(gridModel.getGrid());
            // Log.d("grideventhandler", "new model update ");
        }
        if (gridAdapter != null) {
            gridAdapter.setGridData(gridModel.getGrid());
            // Log.d("grideventhandler", "new grid update ");
        }
    }

    public void unregister() {
        EventBus.getDefault().unregister(this); // Unregister from EventBus
    }
}
