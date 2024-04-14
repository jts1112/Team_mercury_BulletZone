package edu.unh.cs.cs619.bulletzone.model;

abstract class PowerUpEntity extends FieldEntity {
    int pos;
    PowerUpType type;

    @Override
    public abstract int getIntValue();

    public abstract FieldEntity copy();

    @Override
    public abstract String toString();

    public int getPos() {
        return pos;
    }

    public PowerUpType getType() {
        return type;
    }
}
