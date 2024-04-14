package edu.unh.cs.cs619.bulletzone.model;

import java.util.Optional;

public class PowerUpDecorator implements PowerUpComponent {
    PowerUpComponent prevPowerUp;

    public PowerUpDecorator(PowerUpComponent powerUp) {
        prevPowerUp = powerUp;
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

    public PowerUpComponent getPrevPowerUp() {
        return prevPowerUp;
    }
}
