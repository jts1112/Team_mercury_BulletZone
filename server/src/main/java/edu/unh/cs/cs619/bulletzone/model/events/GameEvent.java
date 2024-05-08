package edu.unh.cs.cs619.bulletzone.model.events;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Comparator;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "move", value = MoveEvent.class),
        @JsonSubTypes.Type(name = "turn", value = TurnEvent.class),
        @JsonSubTypes.Type(name = "spawn", value = SpawnEvent.class),
        @JsonSubTypes.Type(name = "damage", value = DamageEvent.class),
        @JsonSubTypes.Type(name = "remove", value = RemovalEvent.class),
        @JsonSubTypes.Type(name = "credit", value = CreditEvent.class),
        @JsonSubTypes.Type(name = "entrance", value = EntranceEvent.class)
})
public abstract class GameEvent {
    private long timeStamp;
    //private GameEventType eventType;
    private final static Object lock = new Object();

    /**
     * Constructor of events of a specified type.
     *
     */
    protected GameEvent() {
        synchronized (lock) {
            timeStamp = System.currentTimeMillis();
        }
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long newTime) {
        this.timeStamp = newTime;
    }

    /**
     * This is how two events are compared for sorting of events by timestamp.
     * (earlier time stamps come first)
     */
    public static Comparator<GameEvent> eventComparator = (e1, e2) -> {
        Long e1Time = e1.getTimeStamp();
        Long e2Time = e2.getTimeStamp();

        //ascending order
        return e1Time.compareTo(e2Time);
    };

    @Override
    public String toString() {
        return "@" + timeStamp;
    }
}
