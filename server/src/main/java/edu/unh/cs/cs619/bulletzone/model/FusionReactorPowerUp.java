package edu.unh.cs.cs619.bulletzone.model;

public class FusionReactorPowerUp extends PowerUpDecorator {
    PowerUpType type = PowerUpType.FusionReactor;
    PowerUpComponent prevPowerUp;

    public FusionReactorPowerUp(PowerUpComponent powerUp) {
        super(powerUp);
    }

    @Override
    public int getMovementInterval(int moveDelay) {
        return prevPowerUp.getMovementInterval(moveDelay) + 100;
    }

    @Override
    public int getFireInterval(int fireDelay) {
        return prevPowerUp.getFireInterval(fireDelay) / 2;
    }
}
