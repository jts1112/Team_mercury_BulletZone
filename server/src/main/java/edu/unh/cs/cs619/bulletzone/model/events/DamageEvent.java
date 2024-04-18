package edu.unh.cs.cs619.bulletzone.model.events;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DamageEvent extends GameEvent {
    @JsonProperty
    private int position;
    @JsonProperty
    private int rawServerValue;

    public DamageEvent() {}

    /**
     * Constructor to create event to sent to eventBus.
     * @param position The position of the entity to remove.
     * @param rawServerValue The entity's raw server value.
     */
    public DamageEvent( int position, int rawServerValue) {
        this.position = position;
        this.rawServerValue = rawServerValue;
    }

    @Override
    public String toString() {
        return "Damage at " + position + super.toString();
    }
}
