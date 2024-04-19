package edu.unh.cs.cs619.bulletzone.ui;

import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import edu.unh.cs.cs619.bulletzone.replay.GameReplayManager;
import edu.unh.cs.cs619.bulletzone.rest.GridUpdateEvent;


public class GridEventHandler {

    private GridModel gridModel;
    private GridAdapter gridAdapter;
    //private GameReplayManager replayManager;

    public GridEventHandler(GridModel gridModel, GridAdapter gridAdapter) {
        this.gridModel = gridModel;
        this.gridAdapter = gridAdapter;
        //replayManager = GameReplayManager.getInstance();
        EventBus.getDefault().register(this); // Register with EventBus
    }

    @Subscribe
    public void onUpdateGrid(GridUpdateEvent event) {
        int[][] gridData = event.gw.getGrid();
        int[][] terrainData = event.gw.getTerrainGrid();

        if (gridModel != null) {
            gridModel.updateGrid(gridData, terrainData);
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
