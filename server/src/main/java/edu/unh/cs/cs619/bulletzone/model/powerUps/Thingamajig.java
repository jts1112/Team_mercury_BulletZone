package edu.unh.cs.cs619.bulletzone.model.powerUps;
public class Thingamajig extends PowerUpDecorator {
    /**
     *
     * @param powerUp parent powerup
     */
    public Thingamajig(PowerUpComponent powerUp) {
        super(powerUp, PowerUpType.Thingamajig, 1);
    }

    /**
     *
     * @param moveDelay default delay
     * @return accumulated delay
     */
    @Override
    public int getMovementInterval(int moveDelay) {
        return prevPowerUp.getMovementInterval(moveDelay);
    }

    /**
     *
     * @param fireDelay default delay
     * @return accumulated delay
     */
    @Override
    public int getFireInterval(int fireDelay) {
        return prevPowerUp.getFireInterval(fireDelay);
    }
}