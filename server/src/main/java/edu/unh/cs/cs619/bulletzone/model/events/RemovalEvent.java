package edu.unh.cs.cs619.bulletzone.model.events;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemovalEvent extends GameEvent {
    @JsonProperty
    private long entityId;

    public RemovalEvent() {}

    public RemovalEvent(long entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        return "Remove entity " + entityId + super.toString();
    }
}
