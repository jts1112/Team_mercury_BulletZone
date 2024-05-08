package edu.unh.cs.cs619.bulletzone.model.entities;

import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;

public class PoweredDrillPowerUpEntity extends PowerUpEntity {

    @Override
    public int getIntValue() {
        return 2007;
    }

    @Override
    public FieldEntity copy() {return new PoweredDrillPowerUpEntity();}

    public PoweredDrillPowerUpEntity() {this.type = PowerUpType.PoweredDrill;}

    public PoweredDrillPowerUpEntity(int pos) {
        this.pos = pos;
        this.type = PowerUpType.PoweredDrill;
    }

    @Override
    public String toString() {
        return "D";
    }

}
