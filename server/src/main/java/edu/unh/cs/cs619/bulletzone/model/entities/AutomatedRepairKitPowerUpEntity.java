package edu.unh.cs.cs619.bulletzone.model.entities;

import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;

public class AutomatedRepairKitPowerUpEntity extends PowerUpEntity {

    @Override
    public int getIntValue() {
        return 2009;
    }

    @Override
    public FieldEntity copy() {return new AutomatedRepairKitPowerUpEntity();}

    public AutomatedRepairKitPowerUpEntity() {this.type = PowerUpType.AutomatedRepairKit;}

    public AutomatedRepairKitPowerUpEntity(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "R";
    }

}
