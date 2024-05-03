package edu.unh.cs.cs619.bulletzone.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.unh.cs.cs619.bulletzone.ui.GridCell;
import edu.unh.cs.cs619.bulletzone.ui.GridCellImageMapper;

public class SpawnEvent extends GameEvent {
    @JsonProperty
    private int rawServerValue;
    @JsonProperty
    private int position;

    GridCellImageMapper mapper;

    public SpawnEvent() {}

    /*
     * Constructor for testing only.
     */
    public SpawnEvent(int rawServerValue, int position) {
        this.rawServerValue = rawServerValue;
        this.position = position;
    }

    /*
     Public only for testing.
    */
    public void applyTo(GridCell[][][] board) {
        this.mapper = GridCellImageMapper.getInstance();
        int layerPos = position % 256;
        GridCell cell = board[position / 256][layerPos / 16][layerPos % 16];
        if (cell.getEntityResourceID() == 0) {
            cell.setEntityResourceID(mapper.getEntityImageResource(rawServerValue));
            cell.setRotationForValue(rawServerValue);
        }
    }

    @Override
    public String toString() {
        return "Spawn " + rawServerValue +
                " at " + position +
                super.toString();
    }

}
