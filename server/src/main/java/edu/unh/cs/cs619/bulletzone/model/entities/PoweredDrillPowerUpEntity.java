package edu.unh.cs.cs619.bulletzone.model.entities;

import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;

public class PoweredDrillPowerUpEntity extends PowerUpEntity {
    int pos;
    PowerUpType type = PowerUpType.PoweredDrill;

    @Override
    public int getIntValue() {
        return 2007;
    }

    @Override
    public FieldEntity copy() {return new PoweredDrillPowerUpEntity();}

    public PoweredDrillPowerUpEntity() {}

    public PoweredDrillPowerUpEntity(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "D";
    }

    /**
     *
     * @return powerUpType
     */
    @Override
    public PowerUpType getType() {
        return type;
    }
}
