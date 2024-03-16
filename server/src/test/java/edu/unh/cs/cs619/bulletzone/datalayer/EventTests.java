/*
Tests for retrieving server-side event history, including tests for showing the longest any
history is maintained (at least 1 minute, but no more than 3 minutes)
 */
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

        history.onEventNotification(new MoveEvent(1, 100, 101));
        history.onEventNotification(new DamageEvent(101, 20));

        Assert.assertEquals("History size should be 2", 2, history.getHistory().size());
    }

    @Test
    public void testGetHistorySinceTimestamp() {
        EventHistory history = EventHistory.getInstance();
        history.clearHistory();

        long testTimestamp = System.currentTimeMillis();

        GameEvent pastEvent = new MoveEvent(1, 50, 51);
        pastEvent.setTimeStamp(testTimestamp - 10000);
        history.onEventNotification(pastEvent);

        GameEvent recentEvent = new DamageEvent(51, 20);
        recentEvent.setTimeStamp(testTimestamp + 5000);
        history.onEventNotification(recentEvent);

        Assert.assertEquals("History since timestamp should have 1 event", 1, history.getHistory(testTimestamp).size());
    }

    @Test
    public void testHistoryDurationLimit() throws InterruptedException {
        EventHistory history = EventHistory.getInstance();
        history.clearHistory();

        history.onEventNotification(new MoveEvent(2, 150, 151));

        Thread.sleep(history.getMillisToKeepHistory() + 1000);

        history.getHistory();

        Assert.assertEquals("History should be empty after duration limit", 0, history.getHistory().size());
    }
}
