package edu.unh.cs.cs619.bulletzone.model.powerUps;

import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;

import java.util.Timer;
import java.util.TimerTask;

public class DeflectorShieldPowerUp extends TimedPowerUpDecorator {
    int shield_health = 50;
    private final Object monitor = new Object();

    /**
     * @param powerUp        parent power-up
     * @param affectedEntity entity affected by the powerup
     */
    public DeflectorShieldPowerUp(PowerUpComponent powerUp, PlayableEntity affectedEntity) {
        super(powerUp, PowerUpType.DeflectorShield, 1, 1, affectedEntity, 300);

        timedAction();
    }

    /**
     * @param powerUp        parent power-up
     * @param time time the power-up has left
     * @param interval interval for the timed action
     * @param affectedEntity entity affected by the power-up
     */
    public DeflectorShieldPowerUp(PowerUpComponent powerUp, int time, int interval, PlayableEntity affectedEntity) {
        super(powerUp, PowerUpType.DeflectorShield, time, interval, affectedEntity, 300);

        timedAction();
    }

    @Override
    public DamageTuple<Integer, Boolean> getModifiedDamageToHolder(DamageTuple<Integer, Boolean> damage) {
        DamageTuple<Integer, Boolean> mod_damage = prevPowerUp.getModifiedDamageToHolder(damage);

        if (!mod_damage.shielded) {
            shield_health -= mod_damage.damage;
            if(shield_health < 0) {
                removeSelf();
                //leftover damage affects entity
                return new DamageTuple<>(shield_health * -1, true);
            } else {
                return new DamageTuple<>(0, true);
            }
        } else {
            return mod_damage;
        }
    }

    @Override
    public void timedAction() {
        Timer timedActionTimer = new Timer();
        timedActionTimer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                synchronized (monitor) {
                    // check that the power-up isn't totally broken before regenerating armor
                    if (shield_health < 0) {
                        removeSelf();
                    } else if (shield_health < 50) {
                        shield_health += 1;
                    }
                }
            }
        }, 0, interval * 1000L);
    }


}
