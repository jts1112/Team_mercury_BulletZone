package edu.unh.cs.cs619.bulletzone.events;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DamageEvent extends GameEvent {
    @JsonProperty
    private long entityId;
    @JsonProperty
    private int damageAmount;

    public DamageEvent() {}

    @Override
    void applyTo(int[][] board) {
        Position pos = findPositionById(entityId);
        if (pos != null) {
            applyDamageToEntityAtPosition(pos, damageAmount);
        }
    }

    public DamageEvent(long entityId, int damageAmount) {
        this.entityId = entityId;
        this.damageAmount = damageAmount;
    }

    @Override
    public String toString() {
        return "Damage " + entityId + " by " + damageAmount + super.toString();
    }
}
