package edu.unh.cs.cs619.bulletzone.model.powerUps;


public class AntiGravPowerUp extends PowerUpDecorator {
    /**
     *
     * @param powerUp parent powerup
     */
    public AntiGravPowerUp(PowerUpComponent powerUp) {
        super(powerUp, PowerUpType.AntiGrav, 300);
    }

    /**
     *
     * @param moveDelay default delay
     * @return accumulated delay
     */
    @Override
    public int getMovementInterval(int moveDelay) {
        return prevPowerUp.getMovementInterval(moveDelay) / 2;
    }

    /**
     *
     * @param fireDelay default delay
     * @return accumulated delay
     */
    @Override
    public int getFireInterval(int fireDelay) {
        return prevPowerUp.getFireInterval(fireDelay) + 100;
    }
}
