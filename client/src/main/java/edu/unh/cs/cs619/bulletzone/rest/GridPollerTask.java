package edu.unh.cs.cs619.bulletzone.rest;

import android.os.SystemClock;
import android.text.style.UpdateAppearance;
import android.util.Log;

import edu.unh.cs.cs619.bulletzone.events.UpdateBoardEvent;

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
    private boolean updateUsingEvents = false;

    public boolean toggleEventUsage() {
        updateUsingEvents = !updateUsingEvents;
        return updateUsingEvents;
    }

    @Background(id = "grid_poller_task")
    public void doPoll() {
        while (true) {
            if (previousTimeStamp < 0) {
                // Initial grid update
                GridWrapper grid = restClient.grid();
                onGridUpdate(grid);
                previousTimeStamp = grid.getTimeStamp();
            } else if (updateUsingEvents) {
                // Update using events
                GameEventCollectionWrapper events = restClient.events(previousTimeStamp);
                if (events != null && !events.getEvents().isEmpty()) {
                    for (GameEvent event : events.getEvents()) {
                        processEvent(event);
                        previousTimeStamp = Math.max(previousTimeStamp, event.getTimeStamp());
                    }
                    EventBus.getDefault().post(new UpdateBoardEvent());                 }
            }

            SystemClock.sleep(100);
        }
    }

    @UiThread
    public void onGridUpdate(GridWrapper gw) {
        EventBus.getDefault().post(new GridUpdateEvent(gw));
    }

    private void processEvent(GameEvent event) {
        switch (event.getType()) {
            case MOVEMENT:
                updateTankPosition(event.getTankId(), event.getNewPositionX(), event.getNewPositionY());
                break;
            case FIRING:
                addBullet(event.getTankId(), event.getBulletId(), event.getPositionX(), event.getPositionY(), event.getDirection());
                break;
            case DAMAGE:
            case DESTRUCTION:
                handleDamageOrDestruction(event.getTargetType(), event.getTargetId());
                break;
            default:
                System.out.println("Unhandled event type: " + event.getType());
                break;
        }
    }

    private void updateTankPosition(int tankId, int newX, int newY) {
    }

    private void addBullet(int tankId, int bulletId, int startX, int startY, byte direction) {
    }

    private void handleDamageOrDestruction(String targetType, int targetId) {
    }
}
