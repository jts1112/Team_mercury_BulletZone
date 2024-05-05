package edu.unh.cs.cs619.bulletzone.model.entities;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;

public class AntiGravPowerUpEntity extends PowerUpEntity {

    @Override
    public int getIntValue() {
        return 2002;
    }

    @Override
    public FieldEntity copy() {return new AntiGravPowerUpEntity();}

    public AntiGravPowerUpEntity() {}

    public AntiGravPowerUpEntity(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "A";
    }

}
