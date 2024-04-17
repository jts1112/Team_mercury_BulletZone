package edu.unh.cs.cs619.bulletzone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;

import edu.unh.cs.cs619.bulletzone.rest.BZRestErrorhandler;
import edu.unh.cs.cs619.bulletzone.rest.BulletZoneRestClient;
import edu.unh.cs.cs619.bulletzone.util.BooleanWrapper;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;
import edu.unh.cs.cs619.bulletzone.util.ShakeDetector;
import edu.unh.cs.cs619.bulletzone.util.UnitIds;

/**
 * Controller class for the client activity UI invoked movement actions
 * Handles the restClient calls previously done by client activity and
 * uses shakeDetector to call
 */
@EBean
public class ActionController {

    @RestService
    public
    BulletZoneRestClient restClient;

    @Bean
    BZRestErrorhandler bzRestErrorhandler;

    // public only for testing
    public UnitIds Ids;
    private long currentUnitId = -1;
    private ShakeDetector shakeDetector;


    public ActionController() {
    }

    // Method to initialize the ActionController with context
    public void initialize(Context context) {
        this.Ids = UnitIds.getInstance();
        restClient.setRestErrorHandler(bzRestErrorhandler);
        shakeDetector = new ShakeDetector(context);
        shakeDetector.setOnShakeListener(() -> {
            // Call onButtonFire when shake is detected
            // Log.d("Action Controller", "Shake detected");
            if (currentUnitId != -1) {
                onButtonFire();
            }
        });
    }

    public long join() {
        try {
            LongWrapper units = restClient.join();
            Ids.setIds(units.getResult(), units.getResult2(), units.getResult3());
            currentUnitId = Ids.getDropshipId();
            return currentUnitId;
        } catch (Exception ignored) {
        }
        return -1;
    }

    public void updateCurrentUnit(String unit) {
        switch (unit) {
            case "dropship":
                currentUnitId = Ids.getDropshipId();
                break;
            case "miner":
                currentUnitId = Ids.getMinerId();
                break;
            case "tank":
                currentUnitId = Ids.getTankId();
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Background
    public void onButtonMove(int viewId) {
        byte direction = 0;

        switch (viewId) {
            case R.id.buttonUp:
                direction = 0;
                break;
            case R.id.buttonDown:
                direction = 4;
                break;
            case R.id.buttonLeft:
                direction = 6;
                break;
            case R.id.buttonRight:
                direction = 2;
                break;
            default:
                Log.e("ActionController", "Unknown movement button id: " + viewId);
                break;
        }

//        Log.d("ActionController", "move called on id = " + currentUnitId);
        restClient.move(currentUnitId, direction);
    }


    @Background
    public void onButtonFire() {
        if (currentUnitId == Ids.getDropshipId()) {
            BooleanWrapper fired = restClient.fire(currentUnitId, (byte) 3);
        } else {
            BooleanWrapper fired = restClient.fire(currentUnitId);
        }
    }

    public void onButtonMine() {
        restClient.mine(Ids.getMinerId());
    }

    public void leave() {
        restClient.leave(Ids.getDropshipId());
    }

    // Only used for testing
    public void setCurrentUnitId(long id) {
        this.currentUnitId = id;
    }

    public void leave(long id) {
        restClient.leave(id);
    }

    @Background
    public void moveToPosition(int targetX, int targetY) {
         restClient.moveToPosition(currentUnitId, targetX, targetY);
    }

}
