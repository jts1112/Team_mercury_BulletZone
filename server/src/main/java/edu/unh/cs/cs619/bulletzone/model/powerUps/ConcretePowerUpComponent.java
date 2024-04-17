package edu.unh.cs.cs619.bulletzone.model.powerUps;

import java.util.Optional;

public class ConcretePowerUpComponent implements PowerUpComponent {
    @Override
    public int getMovementInterval(int moveDelay) {
        return moveDelay;
    }

    @Override
    public int getFireInterval(int fireDelay) {
        return fireDelay;
    }


    @Override
    public Optional<PowerUpComponent> getPrevPowerUp() {
        return Optional.empty();
    }


    @Override
    public Optional<PowerUpType> getPowerUpType() {
        return Optional.empty();
    }
}
