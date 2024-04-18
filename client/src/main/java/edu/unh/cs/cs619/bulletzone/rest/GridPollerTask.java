package edu.unh.cs.cs619.bulletzone.rest;
import static edu.unh.cs.cs619.bulletzone.ClientActivity.updateHealthBar;

import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;
import org.greenrobot.eventbus.EventBus;
import edu.unh.cs.cs619.bulletzone.events.GameEvent;
import edu.unh.cs.cs619.bulletzone.replay.GameReplayManager;
import edu.unh.cs.cs619.bulletzone.util.GameEventCollectionWrapper;
import edu.unh.cs.cs619.bulletzone.util.GridWrapper;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Updated the grid poller to use a scheduled thread
 */
@EBean
public class GridPollerTask {
    @RestService
    BulletZoneRestClient restClient;

    private final ScheduledThreadPoolExecutor scheduler;
    private long previousTimeStamp = -1;
    //private int polls = 0;
    private boolean updateUsingEvents = false;

    public GridPollerTask() {
        this.scheduler = new ScheduledThreadPoolExecutor(1);
    }

    public boolean toggleEventUsage() {
        updateUsingEvents = !updateUsingEvents;
        return updateUsingEvents;
    }

    public void startPolling() {
        scheduler.scheduleAtFixedRate(this::pollServer, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void stopPolling() {
        scheduler.shutdown();
    }

    @Background
    public void pollServer() {
        if (!updateUsingEvents) {
            //Log.d("Poller", "Updating whole grid");
            GridWrapper grid = restClient.grid();
            onGridUpdate(grid);
        } else {
            GameEventCollectionWrapper events = restClient.events(previousTimeStamp);
            boolean haveEvents = false;
            for (GameEvent event : events.getEvents()) {
                EventBus.getDefault().post(event);
                previousTimeStamp = event.getTimeStamp();
                haveEvents = true;
            }
            if (haveEvents) {
                onGridUpdate(restClient.grid()); // Update grid after processing events
            }
        }
    }
    @UiThread
    public void onGridUpdate(GridWrapper gw) {
        EventBus.getDefault().post(new GridUpdateEvent(gw));
        //Log.d("poller", "new gridupdateevent " + gw.getGrid());
        updateHealthBar();  // Tentative
    }
}
