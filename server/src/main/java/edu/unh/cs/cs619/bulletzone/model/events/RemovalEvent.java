package edu.unh.cs.cs619.bulletzone.model.events;
import com.fasterxml.jackson.annotation.JsonProperty;
public class RemovalEvent extends GameEvent {
    @JsonProperty
    private int position;

    public RemovalEvent() {}

    /**
     * Constructor to create event to sent to eventBus.
     * @param position The position of the entity to remove.
     */
    public RemovalEvent(int position) {
        this.position = position;
    }


    @Override
    public String toString() {
        return "Removed position " + position + super.toString();
    }
}
