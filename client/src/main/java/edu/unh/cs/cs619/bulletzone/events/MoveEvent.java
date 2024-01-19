package edu.unh.cs.cs619.bulletzone.events;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoveEvent extends GameEvent {
    @JsonProperty
    private int rawServerValue;
    @JsonProperty
    private int oldPosition;
    @JsonProperty
    private int newPosition;

    public MoveEvent() {}

    void applyTo(int [][]board) {
        board[oldPosition / 16][oldPosition % 16] = 0; //clear old position
        board[newPosition / 16][newPosition % 16] = rawServerValue;
    }

    @Override
    public String toString() {
        return "Move " + rawServerValue +
                " from " + oldPosition +
                " to " + newPosition +
                super.toString();
    }
}
