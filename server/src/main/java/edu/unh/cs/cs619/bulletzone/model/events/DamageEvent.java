package edu.unh.cs.cs619.bulletzone.model.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.unh.cs.cs619.bulletzone.model.events.GameEvent;

public class DamageEvent extends GameEvent {
    @JsonProperty
    private long entityId;
    @JsonProperty
    private int damageAmount;

    public DamageEvent() {}

    public DamageEvent(long entityId, int damageAmount) {
        this.entityId = entityId;
        this.damageAmount = damageAmount;
    }

    @Override
    public String toString() {
        return "Damage " + entityId + " by " + damageAmount + super.toString();
    }
}
