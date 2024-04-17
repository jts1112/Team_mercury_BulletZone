package edu.unh.cs.cs619.bulletzone.model.powerUps;

import java.util.Optional;

public interface PowerUpComponent {
    int getMovementInterval( int moveDelay);

    int getFireInterval(int fireDelay);

    Optional<PowerUpComponent> getPrevPowerUp();

    Optional<PowerUpType> getPowerUpType();
}
