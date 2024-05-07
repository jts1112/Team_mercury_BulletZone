package edu.unh.cs.cs619.bulletzone.model.entities;

import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;

public class FusionReactorPowerUpEntity extends PowerUpEntity {
    @Override
    public int getIntValue() {
        return 2003;
    }

    @Override
    public FieldEntity copy() {
        return new FusionReactorPowerUpEntity();
    }

    public FusionReactorPowerUpEntity() {this.type = PowerUpType.FusionReactor;}

    public FusionReactorPowerUpEntity(int pos) {
        this.pos = pos;
        this.type = PowerUpType.FusionReactor;
    }

    @Override
    public String toString() {
        return "F";
    }

}
