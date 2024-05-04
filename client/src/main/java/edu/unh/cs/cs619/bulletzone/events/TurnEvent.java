package edu.unh.cs.cs619.bulletzone.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.unh.cs.cs619.bulletzone.ui.GridCell;
import edu.unh.cs.cs619.bulletzone.ui.GridCellImageMapper;

public class TurnEvent extends GameEvent{
    @JsonProperty
    private int rawServerValue;
    @JsonProperty
    private String oldDirection;
    @JsonProperty
    private String newDirection;
    @JsonProperty
    private int position;

    public TurnEvent() {}

    public TurnEvent(int rawServerValue, String oldDir, String newDir, int pos) {
        this.rawServerValue = rawServerValue;
        this.oldDirection = oldDir;
        this.newDirection = newDir;
        this.position = pos;
    }

    public void applyTo(GridCell[][][] board) {
        int layerPos = position % 256;
        GridCell cell = board[position / 256][layerPos / 16][layerPos % 16];
        cell.setRotationForValue(rawServerValue);
    }

    @Override
    public String toString() {
        return "Turn " + rawServerValue +
                " from " + oldDirection +
                " to " + newDirection +
                " on " + position +
                super.toString();
    }
}
