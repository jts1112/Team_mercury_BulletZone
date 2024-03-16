package edu.unh.cs.cs619.bulletzone.events;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DamageEvent extends GameEvent {
    @JsonProperty
    private int position;
    @JsonProperty
    private int rawServerValue;

    public DamageEvent( int position, int damageAmount) {
        this.position = position;
        this.rawServerValue = damageAmount;
    }

    @Override
    void applyTo(int[][] board) {
        int row = position / 16;
        int col = position % 16;

        board[row][col] = rawServerValue;
    }

    @Override
    public String toString() {
        return "Damage at " + position + super.toString();
    }
}
