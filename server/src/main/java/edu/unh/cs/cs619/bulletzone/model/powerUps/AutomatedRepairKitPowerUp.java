package edu.unh.cs.cs619.bulletzone.model.powerUps;

import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;

import java.util.Timer;
import java.util.TimerTask;

public class AutomatedRepairKitPowerUp extends TimedPowerUpDecorator {
    private final Object monitor = new Object();

    /**
     * @param powerUp        parent power-up
     * @param affectedEntity  entity being repaired
     */
    public AutomatedRepairKitPowerUp(PowerUpComponent powerUp, PlayableEntity affectedEntity) {
        super(powerUp, PowerUpType.AutomatedRepairKit, 120, 1, affectedEntity, 200);

        timedAction();
    }

    /**
     * @param powerUp        parent power-up
     * @param time time the AutomatedRepairKit lasts
     * @param interval interval of countdown and repairing
     * @param affectedEntity entity being repaired
     */
    public AutomatedRepairKitPowerUp(PowerUpComponent powerUp, int time, int interval, PlayableEntity affectedEntity) {
        super(powerUp, PowerUpType.AutomatedRepairKit, time, interval, affectedEntity, 200);

        timedAction();
    }

    @Override
    public void timedAction() {
        Timer timedActionTimer = new Timer();
        timedActionTimer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                synchronized (monitor) {
                    // check that the repair kit isn't out of time before repairing
                    if (time < 0) {
                        removeSelf();
                    } else if (affectedEntity.getLife() < affectedEntity.getMaxLife()) {
                        System.out.println("Healing " + affectedEntity.toString() + " for " + interval);
                        affectedEntity.setLife(affectedEntity.getLife() + interval);
                        System.out.println("health now at " + affectedEntity.getLife());
                    }
                }
            }
        }, 0, interval * 1000L);
    }


}

