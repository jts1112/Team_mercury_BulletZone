package edu.unh.cs.cs619.bulletzone.events;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    public void applyTo(int[][] board) {
        int row = position / 16;
        int col = position % 16;
        board[row][col] = 0;
    }

    @Override
    public String toString() {
        return "Removed position " + position + super.toString();
    }
}
