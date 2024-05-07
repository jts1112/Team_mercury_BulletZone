package edu.unh.cs.cs619.bulletzone;

import android.content.Context;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.rest.spring.annotations.RestService;
import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.unh.cs.cs619.bulletzone.events.CreditEvent;
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
    public BulletZoneRestClient restClient;
    @Bean
    BZRestErrorhandler bzRestErrorhandler;
    public UnitIds Ids; // public only for testing
    private long currentUnitId = -1;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    // ---------------------------------- Initialization ----------------------------------

    public ActionController() {}

    public void initialize(Context context) {
        this.Ids = UnitIds.getInstance();
        restClient.setRestErrorHandler(bzRestErrorhandler);
        ShakeDetector shakeDetector = new ShakeDetector(context);
        shakeDetector.setOnShakeListener(() -> {
            // Call onButtonFire when shake is detected
            if (currentUnitId != -1) {
                onButtonFire();
            }
        });
    }

    public long join() {
        try {
            LongWrapper units = restClient.join();
            long dropshipId = units.getResult();
            long minerId = units.getResult2();
            long tankId = units.getResult3();
            Ids.setIds(dropshipId);
            int a = R.drawable.tank_icon_full0;
            Integer[] tankImageResources = new Integer[3];
            tankImageResources[0] = R.drawable.tank_icon_full0;
            tankImageResources[1] = R.drawable.tank_icon_low0;
            tankImageResources[2] = R.drawable.tank_icon_very_low0;
            Ids.addTankId(tankId, tankImageResources);
            Integer[] minerImageResources = new Integer[3];
            minerImageResources[0] = R.drawable.miner_icon_full0;
            minerImageResources[1] = R.drawable.miner_icon_low0;
            minerImageResources[2] = R.drawable.miner_icon_very_low0;
            Ids.addMinerId(minerId, minerImageResources);
            currentUnitId = Ids.getDropshipId();
            return currentUnitId;
        } catch (Exception ignored) {
        }
        return -1;
    }

    // ---------------------------------- Top Row Buttons ----------------------------------

    public void updateCurrentUnit(String unit) {
        switch (unit) {
            case "dropship":
                currentUnitId = Ids.getDropshipId();
                break;
            case "miner":
                currentUnitId = Ids.getNextMinerId();
                break;
            case "tank":
                currentUnitId = Ids.getNextTankId();
                break;
        }
        Ids.setControlledUnitId(currentUnitId);
        EventBus.getDefault().post(new CreditEvent(0));
    }

    // ---------------------------------- 2nd Row Buttons ----------------------------------

    public void spawnUnit(String unit) {
        long shipId = Ids.getDropshipId();
        long entityId;
        if (unit.equals("tank")) {
            Future<LongWrapper> future=executorService.submit(()->restClient.spawnTank(shipId));
            try {
                LongWrapper idWrapper = future.get(10, TimeUnit.SECONDS);
                assert idWrapper != null;
                entityId = idWrapper.getResult();
                Ids.addTankId(entityId, getNextTankImageResource());
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } else {
            Future<LongWrapper> future=executorService.submit(()->restClient.spawnMiner(shipId));
            try {
                LongWrapper idWrapper = future.get(10, TimeUnit.SECONDS);
                assert idWrapper != null;
                entityId = idWrapper.getResult();
                Ids.addMinerId(entityId, getNextMinerImageResource());
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        currentUnitId = entityId;
        Ids.setControlledUnitId(currentUnitId);
        EventBus.getDefault().post(new CreditEvent(0));
    }

    private Integer[] getNextTankImageResource() {
        int size = Ids.getTankIdSet().size();
        Integer[] imageResources = new Integer[3];
        imageResources[0] = R.drawable.tank_icon_full0 + size;
        imageResources[1] = R.drawable.tank_icon_low0 + size;
        imageResources[2] = R.drawable.tank_icon_very_low0 + size;
        return imageResources;
    }

    private Integer[] getNextMinerImageResource() {
        int size = Ids.getMinerIdSet().size();
        Integer[] imageResources = new Integer[3];
        imageResources[0] = R.drawable.miner_icon_full0 + size;
        imageResources[1] = R.drawable.miner_icon_low0 + size;
        imageResources[2] = R.drawable.miner_icon_very_low0 + size;
        return imageResources;
    }


    // ---------------------------------- Move Buttons ----------------------------------

    @Background
    public void onButtonMove(byte direction) {
        restClient.move(currentUnitId, direction);
    }

    // ---------------------------------- 2nd to Last Row Buttons ----------------------------------

    public void onButtonTunnel() {
        restClient.dig(currentUnitId);
    }

    @Background
    public void onButtonFire() {
        if (currentUnitId == Ids.getDropshipId()) {
            BooleanWrapper fired = restClient.fire(currentUnitId, (byte) 3);
        } else {
            BooleanWrapper fired = restClient.fire(currentUnitId);
        }
    }

    @Background
    public void moveToPosition(int targetX, int targetY) {
        restClient.moveToPosition(currentUnitId, targetX, targetY);
    }

    public void onButtonMine() {
        restClient.mine(Ids.getControlledUnitId());
    }

    // ---------------------------------- Bottom Row Buttons ----------------------------------

    @Background
    void leaveAsync() {
        System.out.println("Leave called, leaving game");
        BackgroundExecutor.cancelAll("grid_poller_task", true);
        restClient.leave(Ids.getDropshipId());
    }

    public void onButtonEjectPowerUp() {
        restClient.ejectPowerUp(this.currentUnitId);
    }

    // Only used for testing
    public void setCurrentUnitId(long id) {
        this.currentUnitId = id;
    }
}
