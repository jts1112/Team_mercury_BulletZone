package edu.unh.cs.cs619.bulletzone.rest;

import android.os.SystemClock;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;

import org.greenrobot.eventbus.EventBus;

import java.util.Collection;

import edu.unh.cs.cs619.bulletzone.events.GameEvent;
import edu.unh.cs.cs619.bulletzone.util.GameEventCollectionWrapper;
import edu.unh.cs.cs619.bulletzone.util.GridWrapper;
import edu.unh.cs.cs619.bulletzone.util.ResultWrapper;

/**
 * Created by simon on 10/3/14.
 */
@EBean
public class GridPollerTask {
    @RestService
    BulletZoneRestClient restClient;

    private long previousTimeStamp = -1;

    @Background(id = "grid_poller_task")
    public void doPoll() {
        while (true) {

            GridWrapper grid = restClient.grid();
            onGridUpdate(grid);
            if (previousTimeStamp < 0)
                previousTimeStamp = grid.getTimeStamp();
            else {
                GameEventCollectionWrapper events = restClient.events(previousTimeStamp);
                for (GameEvent event : events.getEvents()) {
                    Log.d("Event-check", event.toString());
                    previousTimeStamp = event.getTimeStamp();
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
