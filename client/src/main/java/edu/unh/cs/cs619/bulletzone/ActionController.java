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
    private long tankId = -1;
    private ShakeDetector shakeDetector;
    public ActionController() { }

    public void initialize(Context context) {  // Initialize ActionController with context
        restClient.setRestErrorHandler(bzRestErrorhandler);
        shakeDetector = new ShakeDetector(context);
        shakeDetector.setOnShakeListener(() -> {
            // Log.d("Action Controller", "Shake detected");
            if (tankId != -1) {
                onButtonFire(tankId);  // Call onButtonFire when shake is detected
            }
        });
    }

    public long join() {
        try {
            tankId = restClient.join().getResult();
            return tankId;
        } catch (Exception ignored) { }
        return -1;
    }

    @SuppressLint("NonConstantResourceId")
    @Background
    public void onButtonMove(long tankId, int viewId) {
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
        restClient.move(tankId, direction);
    }

    // Move and turn merged into one action for client side, server side differentiates turn/move
    @Background
    public void onButtonTurn(long tankId, byte direction) {
        restClient.turn(tankId, direction);
    }

    @Background
    public void onButtonFire(long tankId) {
        // Log.d("ActionController", "Fire called.");
        restClient.fire(tankId);
    }

    public void leave(long tankId) {
        restClient.leave(tankId);
    }
}
