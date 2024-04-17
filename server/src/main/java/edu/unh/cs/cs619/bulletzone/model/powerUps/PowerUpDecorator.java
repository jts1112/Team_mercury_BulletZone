package edu.unh.cs.cs619.bulletzone.model.powerUps;

import java.util.Optional;

public class PowerUpDecorator implements PowerUpComponent {
    PowerUpComponent prevPowerUp;
    Optional<PowerUpType> type = Optional.empty();

    public PowerUpDecorator(PowerUpComponent powerUp, PowerUpType type) {
        prevPowerUp = powerUp;
        this.type = Optional.of(type);
    }

    @Override
    public int getMovementInterval(int moveDelay) {
        return prevPowerUp.getMovementInterval(moveDelay);
    }

    @Override
    public int getFireInterval(int fireDelay) {
        return prevPowerUp.getFireInterval(fireDelay);
    }

    public void setPrevPowerUp(PowerUpComponent powerUp) {
        prevPowerUp = powerUp;
    }

    public Optional<PowerUpComponent> getPrevPowerUp() {
        return Optional.of(prevPowerUp);
    }

    @Override
    public Optional<PowerUpType> getPowerUpType() {
        return type;
    }


}
