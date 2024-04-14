package edu.unh.cs.cs619.bulletzone.model;

public class AntiGravPowerUp extends PowerUpDecorator {
    PowerUpType type = PowerUpType.AntiGrav;
    public AntiGravPowerUp(PowerUpComponent powerUp) {
        super(powerUp);
    }

    @Override
    public int getMovementInterval(int moveDelay) {
        return prevPowerUp.getMovementInterval(moveDelay) / 2;
    }

    @Override
    public int getFireInterval(int fireDelay) {
        return prevPowerUp.getFireInterval(fireDelay) + 100;
    }
}
