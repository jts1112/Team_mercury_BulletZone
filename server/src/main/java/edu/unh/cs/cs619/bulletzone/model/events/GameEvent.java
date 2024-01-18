package edu.unh.cs.cs619.bulletzone.model.events;

import java.util.Comparator;

//This class is adapted from group Alpha's project from 2020, courtesy Gersi Doko
public class GameEvent {
    private long timeStamp;
    private GameEventType eventType;
    private final static Object lock = new Object();

    /**
     * Default constructor that constructs a "null" event
     */
    /*
    public GameEvent() {
        this.eventType = -1;
        synchronized (lock) {
            timeStamp = System.currentTimeMillis();
        }
    }
     */

    /**
     * Constructor of events of a specified type.
     *
     * @param type specifies the type of event that the client has to reconstruct
     */
    protected GameEvent(GameEventType type) {
        this.eventType = type;
        synchronized (lock) {
            timeStamp = System.currentTimeMillis();
        }
    }

    public long getTime() {
        return timeStamp;
    }

    public void setTime(long newTime) {
        this.timeStamp = newTime;
    }

    /**
     * This is how two events are compared for sorting of events by timestamp.
     * (earlier time stamps come first)
     */
    public static Comparator<GameEvent> eventComparator = (e1, e2) -> {
        Long e1Time = e1.getTime();
        Long e2Time = e2.getTime();

        //ascending order
        return e1Time.compareTo(e2Time);
    };
}
