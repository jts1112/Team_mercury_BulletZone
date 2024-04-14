package edu.unh.cs.cs619.bulletzone.model;

public class FusionReactorPowerUpEntity extends FieldEntity {
    int pos;
    PowerUpType type = PowerUpType.FusionReactor;

    @Override
    public int getIntValue() {
        return 2003;
    }

    @Override
    public FieldEntity copy() {
        return new FusionReactorPowerUpEntity();
    }

    public FusionReactorPowerUpEntity() {}

    public FusionReactorPowerUpEntity(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "F";
    }
}
