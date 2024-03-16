package edu.unh.cs.cs619.bulletzone.rest;
import android.os.SystemClock;
import edu.unh.cs.cs619.bulletzone.events.UpdateBoardEvent;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;
import org.greenrobot.eventbus.EventBus;
import edu.unh.cs.cs619.bulletzone.events.GameEvent;
import edu.unh.cs.cs619.bulletzone.util.GameEventCollectionWrapper;
import edu.unh.cs.cs619.bulletzone.util.GridWrapper;

@EBean
public class GridPollerTask {
    @RestService
    BulletZoneRestClient restClient;

    private long previousTimeStamp = -1;
    private int polls = 0;
    private boolean updateUsingEvents = false;

    public boolean toggleEventUsage() {
        updateUsingEvents = !updateUsingEvents;
        return updateUsingEvents;
    }dddddddddddd

    @Background(id = "grid_poller_task")
    public void doPoll() {
        while (true) {
            if (previousTimeStamp < 0 || !updateUsingEvents) {
                //Log.d("Poller", "Updating whole grid");
                GridWrapper grid = restClient.grid();
                onGridUpdate(grid);
                previousTimeStamp = grid.getTimeStamp();
                if (polls < 1) {
                    updateUsingEvents = true;
                    polls++;
                }

            }
            else {
                //Log.d("Poller", "Updating using events");
                GameEventCollectionWrapper events = restClient.events(previousTimeStamp);
                boolean haveEvents = false;
                for (GameEvent event : events.getEvents()) {
                    //Log.d("Event-check", event.toString());
                    EventBus.getDefault().post(event);
                    previousTimeStamp = event.getTimeStamp();
                    haveEvents = true;
                }
                if (haveEvents) {  // If the server has returned events that occurred since the
                    // last poll
                    long currentTime = System.currentTimeMillis();  // New code 🔽
                    String summary = "Processed " + events.getEvents().size() + " events.";
                    EventBus.getDefault().post(new UpdateBoardEvent(currentTime, summary));
                }
            }

            // poll server every 100ms
            SystemClock.sleep(100);
        }
    }
    @UiThread
    public void onGridUpdate(GridWrapper gw) {
        EventBus.getDefault().post(new GridUpdateEvent(gw));
    }
}
