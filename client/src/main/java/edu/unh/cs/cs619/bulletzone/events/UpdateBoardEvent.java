// Was empty in starter code
package edu.unh.cs.cs619.bulletzone.events;

import java.util.List;

public class UpdateBoardEvent {
    private final long timestamp;
    private final String summary;

    public UpdateBoardEvent(long timestamp, String summary) {
        this.timestamp = timestamp;
        this.summary = summary;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSummary() {
        return summary;
    }
}
