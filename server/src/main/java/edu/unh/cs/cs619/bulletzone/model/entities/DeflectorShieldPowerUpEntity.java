package edu.unh.cs.cs619.bulletzone.model.entities;

import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;

public class DeflectorShieldPowerUpEntity extends PowerUpEntity {

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

}
