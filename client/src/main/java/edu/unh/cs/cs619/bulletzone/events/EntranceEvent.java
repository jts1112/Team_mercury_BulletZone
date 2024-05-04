package edu.unh.cs.cs619.bulletzone.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.unh.cs.cs619.bulletzone.ui.GridCell;
import edu.unh.cs.cs619.bulletzone.ui.GridCellImageMapper;

public class EntranceEvent extends GameEvent {
    @JsonProperty
    private int rawServerValue;
    @JsonProperty
    private int topPosition;

    GridCellImageMapper mapper;

    public EntranceEvent() {}

    /*
     * Constructor for testing only.
     */
    public EntranceEvent(int rawServerValue, int topPosition) {
        this.rawServerValue = rawServerValue;
        this.topPosition = topPosition;
    }

    /*
     Public only for testing.
    */
    public void applyTo(GridCell[][][] board) {
        this.mapper = GridCellImageMapper.getInstance();
        int topLayerPos = topPosition % 256;
        GridCell topCell = board[topPosition / 256][topLayerPos / 16][topLayerPos % 16];
        topCell.setTerrainResourceID(mapper.getTerrainImageResource(rawServerValue));
    }

    @Override
    public String toString() {
        return "Entrance " + rawServerValue +
                " at " + topPosition +
                super.toString();
    }

}
