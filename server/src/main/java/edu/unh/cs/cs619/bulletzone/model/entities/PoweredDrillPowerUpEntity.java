package edu.unh.cs.cs619.bulletzone.model.entities;

import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;

public class PoweredDrillPowerUpEntity extends PowerUpEntity {

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

}
