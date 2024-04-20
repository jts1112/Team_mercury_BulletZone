package edu.unh.cs.cs619.bulletzone.model.powerUps;

public class FusionReactorPowerUp extends PowerUpDecorator {

    /**
     *
     * @param powerUp parent powerup
     */
    public FusionReactorPowerUp(PowerUpComponent powerUp) {
        super(powerUp, PowerUpType.FusionReactor);
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
