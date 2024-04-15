package edu.unh.cs.cs619.bulletzone.events;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.unh.cs.cs619.bulletzone.R;

public class DamageEvent extends GameEvent {
    @JsonProperty
    private int position;
    @JsonProperty
    private int rawServerValue;

    public DamageEvent() {}

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
    public void applyTo(int[][] board, GameData gameData) {
        int row = position / 16;
        int col = position % 16;
        board[row][col] = rawServerValue;
        Log.d("DamageEvent", "server val = " + rawServerValue);
        if (rawServerValue >= 10000000 && rawServerValue <= 20000000) {
            gameData.setTankLife(rawServerValue);
        } else if (rawServerValue >= 20000000 && rawServerValue <= 30000000) {
            gameData.setMinerLife(rawServerValue);
        } else if (rawServerValue >= 30000000 && rawServerValue <= 40000000) {
            gameData.setDropshipLife(rawServerValue);
        }
    }

    @Override
    public String toString() {
        return "Damage to entity at position " + position + super.toString();
    }
}
