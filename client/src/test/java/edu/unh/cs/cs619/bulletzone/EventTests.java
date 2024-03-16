package edu.unh.cs.cs619.bulletzone;

import static org.junit.Assert.assertEquals;

import org.testng.annotations.Test;

import edu.unh.cs.cs619.bulletzone.model.events.DamageEvent;
import edu.unh.cs.cs619.bulletzone.model.events.EventHistory;
import edu.unh.cs.cs619.bulletzone.model.events.MoveEvent;

public class EventTests {
    @Test
    public void testGetFullHistory() {
        EventHistory history = EventHistory.getInstance();
        history.clearHistory();

        history.onEventNotification(new MoveEvent());
        history.onEventNotification(new DamageEvent());

        assertEquals("History size should be 2", 2, history.getHistory().size());
    }
}
