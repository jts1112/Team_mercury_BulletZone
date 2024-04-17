package edu.unh.cs.cs619.bulletzone.model.powerUps;


public class AntiGravPowerUp extends PowerUpDecorator {
    public AntiGravPowerUp(PowerUpComponent powerUp) {
        super(powerUp, PowerUpType.AntiGrav);
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
