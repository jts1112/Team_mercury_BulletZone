package edu.unh.cs.cs619.bulletzone.events;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpawnEvent extends GameEvent {
    @JsonProperty
    private long rawServerValue;
    @JsonProperty
    private int position;

    public SpawnEvent() {}

    public SpawnEvent(int rawServerValue, int pos) {
        //super(GameEventType.Spawn);
        this.rawServerValue = rawServerValue;
        this.position = pos;
    }

    @Override
    public String toString() {
        return "Spawn " + rawServerValue +
                " at " + position +
                super.toString();
    }

}
