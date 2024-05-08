package edu.unh.cs.cs619.bulletzone.model.entities;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;

public class AntiGravPowerUpEntity extends PowerUpEntity {

    @Override
    public int getIntValue() {
        return 2002;
    }

    @Override
    public FieldEntity copy() {return new AntiGravPowerUpEntity();}

    public AntiGravPowerUpEntity() {this.type = PowerUpType.AntiGrav;}

    public AntiGravPowerUpEntity(int pos) {
        this.pos = pos;
        this.type = PowerUpType.AntiGrav;
    }

    @Override
    public String toString() {
        return "A";
    }

}
