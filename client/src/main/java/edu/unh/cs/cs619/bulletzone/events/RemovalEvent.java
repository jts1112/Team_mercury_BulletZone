package edu.unh.cs.cs619.bulletzone.events;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.unh.cs.cs619.bulletzone.ui.GridCell;

public class RemovalEvent extends GameEvent {
    @JsonProperty
    private int position;

    public RemovalEvent() {}

    /**
     * Constructor for testing only.
     * @param position The position of the entity to remove.
     */
    public RemovalEvent(int position) {
        this.position = position;
    }

    /**
     Public only for testing, normally not accessible.
     *
     * @param board The game board.
     */
    @Override
    public void applyTo(GridCell[][][] board) {
        int layerPos = position % 256;
        GridCell cell = board[position / 256][layerPos / 16][layerPos % 16];
        cell.setEntityResourceID(0);
        cell.setRotationForValue(0);
    }

    @Override
    public String toString() {
        return "Removed position " + position + super.toString();
    }
}
