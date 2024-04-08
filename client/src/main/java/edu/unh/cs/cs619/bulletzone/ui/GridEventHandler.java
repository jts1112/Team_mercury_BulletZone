package edu.unh.cs.cs619.bulletzone.ui;

import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import edu.unh.cs.cs619.bulletzone.rest.GridUpdateEvent;


public class GridEventHandler {

    private GridModel gridModel;
    private GridAdapter gridAdapter;

    public GridEventHandler(GridModel gridModel, GridAdapter gridAdapter) {
        this.gridModel = gridModel;
        this.gridAdapter = gridAdapter;
        EventBus.getDefault().register(this); // Register with EventBus
    }

    @Subscribe
    public void onUpdateGrid(GridUpdateEvent event) {
        int[][] gridData = event.gw.getGrid();

        if (gridModel != null) {
            gridModel.updateGrid(gridData);
            // Log.d("grideventhandler", "new model update ");
        }
        if (gridAdapter != null) {
            gridAdapter.setGridData(gridData);
            // Log.d("grideventhandler", "new grid update ");
        }
    }

    public void unregister() {
        EventBus.getDefault().unregister(this); // Unregister from EventBus
    }
}
