package edu.unh.cs.cs619.bulletzone.datalayer;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import edu.unh.cs.cs619.bulletzone.model.events.DamageEvent;
import edu.unh.cs.cs619.bulletzone.model.events.EventHistory;
import edu.unh.cs.cs619.bulletzone.model.events.GameEvent;
import edu.unh.cs.cs619.bulletzone.model.events.MoveEvent;

public class EventTests {
    @Test
    public void testGetFullHistory() {
        EventHistory history = EventHistory.getInstance();
        history.clearHistory();

        // Hypothetical constructors. Replace with actual.
        history.onEventNotification(new MoveEvent(1, 100, 101)); // Tank ID, Old Position, New Position
        history.onEventNotification(new DamageEvent(101, 20)); // Position, Damage Amount

        Assert.assertEquals("History size should be 2", 2, history.getHistory().size());
    }

    @Test
    public void testGetHistorySinceTimestamp() {
        EventHistory history = EventHistory.getInstance();
        history.clearHistory(); // Reset history

        long testTimestamp = System.currentTimeMillis();

        // Hypothetical parameters. Replace with actual.
        GameEvent pastEvent = new MoveEvent(1, 50, 51); // Tank ID, Old Position, New Position
        pastEvent.setTimeStamp(testTimestamp - 10000);
        history.onEventNotification(pastEvent);

        GameEvent recentEvent = new DamageEvent(51, 20); // Position, Damage Amount
        recentEvent.setTimeStamp(testTimestamp + 5000);
        history.onEventNotification(recentEvent);

        Assert.assertEquals("History since timestamp should have 1 event", 1, history.getHistory(testTimestamp).size());
    }

    @Test
    public void testHistoryDurationLimit() throws InterruptedException {
        EventHistory history = EventHistory.getInstance();
        history.clearHistory(); // Clear history for the test

        // Hypothetical parameters. Replace with actual.
        history.onEventNotification(new MoveEvent(2, 150, 151)); // Tank ID, Old Position, New Position

        Thread.sleep(history.getMillisToKeepHistory() + 1000);

        history.getHistory();

        Assert.assertEquals("History should be empty after duration limit", 0, history.getHistory().size());
    }
}
