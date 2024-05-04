package edu.unh.cs.cs619.bulletzone.model.powerUps;

import java.util.Optional;

public interface PowerUpComponent {
    /**
     *
     * @param currentVal current total to increment with the current power-up value
     * @return incremented value
     */
    int getPowerUpValue(int currentVal);
    /**
     *
     * @param moveDelay default delay
     * @return accumulated delay
     */
    int getMovementInterval( int moveDelay);

    /**
     *
     * @param entranceTime default entrance time
     * @return accumulated entrance time
     */
    int getTerrainEntranceInterval( int entranceTime);

    /**
     *
     * @param fireDelay default delay
     * @return accumulated delay
     */
    int getFireInterval(int fireDelay);

    /**
     *
     * @param damage damage to be modified
     * @return accumulated damage
     */
    DamageTuple<Integer, Boolean> getModifiedDamageToHolder(DamageTuple<Integer, Boolean> damage);

    /**
     *
     * @param damage contact damage dealt
     * @return accumulated dealt contact damage
     */
    int getModifiedContactDamageFromHolder(int damage);

    /**
     *
     * @return parent power-up
     */
    Optional<PowerUpComponent> getPrevPowerUp();

    /**
     *
     * @return power-up type
     */
    Optional<PowerUpType> getPowerUpType();
}
