package edu.unh.cs.cs619.bulletzone.events;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DamageEvent extends GameEvent {
    @JsonProperty
    private int position;
    @JsonProperty
    private int rawServerValue;

    /**
     * Constructor for testing only.
     * @param position The position of the entity to remove.
     * @param rawServerValue The raw server value of the entity.
     */
    public DamageEvent( int position, int rawServerValue) {
        this.position = position;
        this.rawServerValue = rawServerValue;
    }

    /**
     * Public only for testing.
     * @param board The game board.
    */
    @Override
    public void applyTo(int[][] board) {
        int row = position / 16;
        int col = position % 16;
        board[row][col] = rawServerValue;
    }

    @Override
    public String toString() {
        return "Damage to entity at position " + position + super.toString();
    }
}
