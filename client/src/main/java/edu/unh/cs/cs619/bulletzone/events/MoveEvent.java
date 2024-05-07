package edu.unh.cs.cs619.bulletzone.events;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.unh.cs.cs619.bulletzone.R;
import edu.unh.cs.cs619.bulletzone.ui.GridCell;
import edu.unh.cs.cs619.bulletzone.ui.GridCellImageMapper;

public class MoveEvent extends GameEvent {
    @JsonProperty
    private int rawServerValue;
    @JsonProperty
    private int oldPosition;
    @JsonProperty
    private int newPosition;

    GridCellImageMapper mapper;

    public MoveEvent() {}

    /*
     * Constructor for testing only.
     */
    public MoveEvent(int rawServerValue, int oldPosition, int newPosition) {
        this.rawServerValue = rawServerValue;
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    /*
     Public only for testing.
    */
    public void applyTo(GridCell[][][] board) {
        this.mapper = GridCellImageMapper.getInstance();
        int resourceId = mapper.getEntityImageResource(rawServerValue);
        int oldlayerPos = oldPosition % 256;
        int newlayerPos = newPosition % 256;
        GridCell cell = board[oldPosition / 256][oldlayerPos / 16][oldlayerPos % 16];
        GridCell newCell = board[newPosition / 256][newlayerPos / 16][newlayerPos % 16];

        if (cell.getEntityResourceID() == resourceId) {
            cell.setEntityResourceID(0);
            cell.setRotationForValue(0);
        }
        int id = newCell.getEntityResourceID();
        if (id != R.drawable.dropship1full && id != R.drawable.dropship1low && id != R.drawable.dropship1verylow) {
            newCell.setEntityResourceID(resourceId);
            newCell.setRotationForValue(rawServerValue);
        }
    }

    @Override
    public String toString() {
        return "Move " + rawServerValue +
                " from " + oldPosition +
                " to " + newPosition +
                super.toString();
    }
}
