package edu.unh.cs.cs619.bulletzone.model.events;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DamageEvent extends GameEvent {
    @JsonProperty
    private int position;
    @JsonProperty
    private int rawServerValue;

    public DamageEvent( int position, int rawServerValue) {
        this.position = position;
        this.rawServerValue = rawServerValue;
    }

    @Override
    public String toString() {
        return "Damage at " + position + super.toString();
    }
}
