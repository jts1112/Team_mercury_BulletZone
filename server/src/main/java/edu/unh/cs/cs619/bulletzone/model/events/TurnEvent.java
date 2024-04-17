package edu.unh.cs.cs619.bulletzone.model.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.unh.cs.cs619.bulletzone.model.Direction;

public class TurnEvent extends GameEvent{
    @JsonProperty
    private int rawServerValue;
    @JsonProperty
    private Direction oldDirection;
    @JsonProperty
    private Direction newDirection;
    @JsonProperty
    private int position;

    public TurnEvent() {}

    public TurnEvent(int rawServerValue, Direction oldDir, Direction newDir, int pos) {
        this.rawServerValue = rawServerValue;
        this.oldDirection = oldDir;
        this.newDirection = newDir;
        this.position = pos;
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
