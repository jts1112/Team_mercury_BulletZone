package edu.unh.cs.cs619.bulletzone.model.powerUps;

import edu.unh.cs.cs619.bulletzone.datalayer.core.Entity;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;

import java.util.Optional;

public abstract class TimedPowerUpDecorator extends PowerUpDecorator {
    /**
     * Time in seconds that the power-up lasts
     */
    int time;
    /**
     * interval to decrement the timer by in seconds
     */
    int interval;
    PlayableEntity affectedEntity;

    /**
     * @param powerUp parent power-up
     * @param type    power-up type
     */
    public TimedPowerUpDecorator(PowerUpComponent powerUp, PowerUpType type, int time, int interval, PlayableEntity affectedEntity, int value) {
        super(powerUp, type, value);

        this.time = time;
        this.interval = interval;
        this.affectedEntity = affectedEntity;
    }

    public abstract void timedAction();

    public void removeSelf() {
        Optional<PowerUpComponent> powerUpChain = Optional.of(affectedEntity.getPowerUps());
        while (powerUpChain.isPresent() && powerUpChain.get() != this) {
             powerUpChain = powerUpChain.get().getPrevPowerUp();
        }
    }
}
