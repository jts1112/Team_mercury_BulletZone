package edu.unh.cs.cs619.bulletzone.model.events;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoveEvent extends GameEvent{
    @JsonProperty
    private int rawServerValue;
    @JsonProperty
    private int oldPosition;
    @JsonProperty
    private int newPosition;

    public MoveEvent() {}

    public MoveEvent(int rawServerValue, int oldPos, int newPos) {
        this.rawServerValue = rawServerValue;
        this.oldPosition = oldPos;
        this.newPosition = newPos;
    }

    @Override
    public String toString() {
        return "Move " + rawServerValue +
                " from " + oldPosition +
                " to " + newPosition +
                super.toString();
    }
}
