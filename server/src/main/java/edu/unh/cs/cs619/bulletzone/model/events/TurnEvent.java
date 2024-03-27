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

    public TurnEvent() {}

    public TurnEvent(int rawServerValue, Direction oldDir, Direction newDir) {
        this.rawServerValue = rawServerValue;
        this.oldDirection = oldDir;
        this.newDirection = newDir;
    }

    @Override
    public String toString() {
        return "Turn " + rawServerValue +
                " from " + oldDirection +
                " to " + newDirection +
                super.toString();
    }
}
