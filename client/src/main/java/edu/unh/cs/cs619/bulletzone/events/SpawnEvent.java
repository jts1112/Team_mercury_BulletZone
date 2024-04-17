package edu.unh.cs.cs619.bulletzone.events;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpawnEvent extends GameEvent {
    @JsonProperty
    private int rawServerValue;
    @JsonProperty
    private int position;

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
    public void applyTo(int[][] board, GameData gameData) {
        board[position / 16][position % 16] = rawServerValue;
    }

    @Override
    public String toString() {
        return "Spawn " + rawServerValue +
                " at " + position +
                super.toString();
    }

}
