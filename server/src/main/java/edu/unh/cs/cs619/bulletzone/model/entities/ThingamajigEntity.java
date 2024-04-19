package edu.unh.cs.cs619.bulletzone.model.entities;

import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;

public class ThingamajigEntity extends PowerUpEntity {
    int pos;
    PowerUpType type = PowerUpType.AntiGrav;

    @Override
    public int getIntValue() {
        return 2004;
    }

    @Override
    public FieldEntity copy() {return new AntiGravPowerUpEntity();}

    public ThingamajigEntity() {}

    public ThingamajigEntity(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "T";
    }
}
