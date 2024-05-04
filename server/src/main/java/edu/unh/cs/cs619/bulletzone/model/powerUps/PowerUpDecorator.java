package edu.unh.cs.cs619.bulletzone.model.powerUps;

import java.util.Optional;

public class PowerUpDecorator implements PowerUpComponent {
    PowerUpComponent prevPowerUp;
    Optional<PowerUpType> type = Optional.empty();

    int value = 0;
    /**
     *
     * @param powerUp parent power-up
     * @param type power-up type
     */
    public PowerUpDecorator(PowerUpComponent powerUp, PowerUpType type, int value) {
        prevPowerUp = powerUp;
        this.type = Optional.of(type);
        this.value = value;
    }

    @Override
    public int getPowerUpValue(int currentVal) {
        return prevPowerUp.getPowerUpValue(currentVal) + value;
    }

    @Override
    public int getMovementInterval(int moveDelay) {
        return prevPowerUp.getMovementInterval(moveDelay);
    }

    @Override
    public int getTerrainEntranceInterval(int entranceTime) {
        return prevPowerUp.getTerrainEntranceInterval(entranceTime);
    }

    @Override
    public int getFireInterval(int fireDelay) {
        return prevPowerUp.getFireInterval(fireDelay);
    }

    @Override
    public DamageTuple<Integer, Boolean> getModifiedDamageToHolder(DamageTuple<Integer, Boolean> damage) {
        return prevPowerUp.getModifiedDamageToHolder(damage);
    }

    @Override
    public int getModifiedContactDamageFromHolder(int damage) {
        return prevPowerUp.getModifiedContactDamageFromHolder(damage);
    }

    /**
     *
     * @param powerUp parent power-up
     */
    public void setPrevPowerUp(PowerUpComponent powerUp) {
        prevPowerUp = powerUp;
    }

    /**
     *
     * @return parent power-up
     */
    public Optional<PowerUpComponent> getPrevPowerUp() {
        return Optional.of(prevPowerUp);
    }

    @Override
    public Optional<PowerUpType> getPowerUpType() {
        return type;
    }


}
