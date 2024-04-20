package edu.unh.cs.cs619.bulletzone.model.powerUps;

import java.util.Optional;

public interface PowerUpComponent {
    /**
     *
     * @param moveDelay default delay
     * @return accumulated delay
     */
    int getMovementInterval( int moveDelay);

    /**
     *
     * @param fireDelay default delay
     * @return accumulated delay
     */
    int getFireInterval(int fireDelay);

    /**
     *
     * @return parent power-up
     */
    Optional<PowerUpComponent> getPrevPowerUp();

    /**
     *
     * @return power-up type
     */
    Optional<PowerUpType> getPowerUpType();
}
