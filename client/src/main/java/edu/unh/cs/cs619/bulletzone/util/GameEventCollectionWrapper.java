package edu.unh.cs.cs619.bulletzone.util;

import java.util.Collection;

import edu.unh.cs.cs619.bulletzone.events.GameEvent;

public class GameEventCollectionWrapper {
    private Collection<GameEvent> events;

    public GameEventCollectionWrapper() {}

    public GameEventCollectionWrapper(Collection<GameEvent> input) {
        this.events = input;
    }

    public Collection<GameEvent> getEvents() {
        return this.events;
    }

    public void setEvents(Collection<GameEvent> set) {  // FIX? Not used anywhere
        this.events = set;
    }
}
