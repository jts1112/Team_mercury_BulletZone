package edu.unh.cs.cs619.bulletzone.model.powerUps;

import java.util.Optional;

public class ConcretePowerUpComponent implements PowerUpComponent {
    @Override
    public int getPowerUpValue(int currentVal) {
        return currentVal;
    }
    @Override
    public int getMovementInterval(int moveDelay) {
        return moveDelay;
    }

    @Override
    public int getTerrainEntranceInterval(int entranceTime) {
        return entranceTime;
    }

    @Override
    public int getFireInterval(int fireDelay) {
        return fireDelay;
    }

    @Override
    public DamageTuple<Integer, Boolean> getModifiedDamageToHolder(DamageTuple<Integer, Boolean> damage) {
        return damage;
    }

    @Override
    public int getModifiedContactDamageFromHolder(int damage) {
        return damage;
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
