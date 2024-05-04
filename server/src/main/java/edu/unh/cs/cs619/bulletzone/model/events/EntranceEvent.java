package edu.unh.cs.cs619.bulletzone.model.events;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EntranceEvent extends GameEvent {
    @JsonProperty
    private int rawServerValue;
    @JsonProperty
    private int topPosition;


    public EntranceEvent() {}

    /*
     * Constructor for testing only.
     */
    public EntranceEvent(int rawServerValue, int topPosition) {
        this.rawServerValue = rawServerValue;
        this.topPosition = topPosition;
    }

    @Override
    public String toString() {
        return "Entrance " + rawServerValue +
                " at " + topPosition +
                super.toString();
    }
}