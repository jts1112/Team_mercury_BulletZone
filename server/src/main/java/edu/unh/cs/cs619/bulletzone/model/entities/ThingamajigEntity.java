package edu.unh.cs.cs619.bulletzone.model.entities;

import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;

public class ThingamajigEntity extends PowerUpEntity {

    @Override
    public int getIntValue() {
        return 2004;
    }

    @Override
    public FieldEntity copy() {return new ThingamajigEntity();}

    public ThingamajigEntity() {this.type = PowerUpType.Thingamajig;}

    public ThingamajigEntity(int pos) {
        this.pos = pos;
        this.type = PowerUpType.Thingamajig;
    }

    @Override
    public String toString() {
        return "T";
    }

}
