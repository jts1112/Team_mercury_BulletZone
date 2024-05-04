package edu.unh.cs.cs619.bulletzone.model.powerUps;

public class PoweredDrillPowerUp extends PowerUpDecorator {
    /**
     * @param powerUp parent power-up
     */
    public PoweredDrillPowerUp(PowerUpComponent powerUp) {
        super(powerUp, PowerUpType.PoweredDrill, 400);
    }

    @Override
    public DamageTuple<Integer, Boolean> getModifiedDamageToHolder(DamageTuple<Integer, Boolean> damage) {
        DamageTuple<Integer, Boolean> mod_damage = prevPowerUp.getModifiedDamageToHolder(damage);
        return new DamageTuple<>(mod_damage.damage / 2, mod_damage.shielded);
    }

    @Override
    public int getModifiedContactDamageFromHolder(int damage) {
        return prevPowerUp.getModifiedContactDamageFromHolder(damage) + 50;
    }

    @Override
    public int getTerrainEntranceInterval(int entranceTime) {
        return prevPowerUp.getTerrainEntranceInterval(entranceTime) + 100;
    }
}
