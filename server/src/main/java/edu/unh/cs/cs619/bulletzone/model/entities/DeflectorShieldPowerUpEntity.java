package edu.unh.cs.cs619.bulletzone.model.entities;

import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;

public class DeflectorShieldPowerUpEntity extends PowerUpEntity {
    int pos;
    PowerUpType type = PowerUpType.DeflectorShield;

    @Override
    public int getIntValue() {
        return 2008;
    }

    @Override
    public FieldEntity copy() {return new DeflectorShieldPowerUpEntity();}

    public DeflectorShieldPowerUpEntity() {}

    public DeflectorShieldPowerUpEntity(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "S";
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
