package edu.unh.cs.cs619.bulletzone.model;

public class AntiGravPowerUpEntity extends PowerUpEntity {
    int pos;
    PowerUpType type = PowerUpType.AntiGrav;

    @Override
    public int getIntValue() {
        return 2002;
    }

    @Override
    public FieldEntity copy() {
        return new AntiGravPowerUpEntity();
    }

    public AntiGravPowerUpEntity() {}

    public AntiGravPowerUpEntity(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "A";
    }
}
