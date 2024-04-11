package edu.unh.cs.cs619.bulletzone;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.http.ResponseEntity;

import edu.unh.cs.cs619.bulletzone.rest.BZRestErrorhandler;
import edu.unh.cs.cs619.bulletzone.rest.BulletZoneRestClient;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;
import edu.unh.cs.cs619.bulletzone.util.ShakeDetector;
/**
 * Controller class for the client activity UI invoked movement actions. Handles restClient
 * calls previously done by clientActivity and uses shakeDetector to call.
 */
@EBean
public class ActionController {
    @RestService
    public
    BulletZoneRestClient restClient;
    @Bean
    BZRestErrorhandler bzRestErrorhandler;
    private long dropshipId = -1;
    private long tankId = -1;
    private long minerId = -1;
    private long currentEntityId = -1;
    private ShakeDetector shakeDetector;
    public ActionController() { }

    public void initialize(Context context) {  // Initialize ActionController with context
        restClient.setRestErrorHandler(bzRestErrorhandler);
        shakeDetector = new ShakeDetector(context);
        shakeDetector.setOnShakeListener(() -> {
            // Log.d("Action Controller", "Shake detected");
            if (dropshipId != -1) {
                onButtonFire(dropshipId);  // Call onButtonFire when shake is detected
            }
        });
    }

    public long join() {
        try {
            LongWrapper response = restClient.join();
            dropshipId = response.getResult();
            Log.d("ActionController", "Id = " + dropshipId);
            return dropshipId;
        } catch (Exception ignored) { }
        return -1;
    }

    @SuppressLint("NonConstantResourceId")
    @Background
    public void onButtonMove(long entityId, int viewId) {
        byte direction = 0;
        switch (viewId) {
            case R.id.buttonUp:
                break;
            case R.id.buttonRight:
                direction = 2;
                break;
            case R.id.buttonDown:
                direction = 4;
                break;
            case R.id.buttonLeft:
                direction = 6;
                break;
            default:
                Log.e("ActionController", "Unknown movement button id: " + viewId);
                break;
        }
        restClient.move(entityId, direction);
    }

    // Move and turn merged into one action for client side, server side differentiates turn/move
//    @Background
//    public void onButtonTurn(long tankId, byte direction) {
//        restClient.turn(tankId, direction);
//    }

    @Background
    public void onButtonFire(long entityId) {
        // Log.d("ActionController", "Fire called.");
        restClient.fire(entityId);
    }

    public void leave(long entityId) {
        restClient.leave(entityId);
    }

    public long spawnMiner() {
        try {
            LongWrapper response = restClient.spawnMiner(dropshipId);
            minerId = response.getResult();
            return minerId;
        } catch (Exception ignored) { }
        return -1;
    }

    public long spawnTank() {
        try {
            LongWrapper response = restClient.spawnTank(dropshipId);
            tankId = response.getResult();
            return tankId;
        } catch (Exception ignored) { }
        return -1;
    }

}
