package edu.unh.cs.cs619.bulletzone.events;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public void applyTo(int[][] board) {
        board[position / 16][position % 16] = rawServerValue;
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
