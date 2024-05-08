package edu.unh.cs.cs619.bulletzone.model.entities;

import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;

public class DeflectorShieldPowerUpEntity extends PowerUpEntity {

    @Override
    public int getIntValue() {
        return 2008;
    }

    @Override
    public FieldEntity copy() {return new DeflectorShieldPowerUpEntity();}

    public DeflectorShieldPowerUpEntity() {this.type = PowerUpType.DeflectorShield;}

    public DeflectorShieldPowerUpEntity(int pos) {
        this.pos = pos;
        this.type = PowerUpType.DeflectorShield;
    }

    @Override
    public String toString() {
        return "S";
    }

}
