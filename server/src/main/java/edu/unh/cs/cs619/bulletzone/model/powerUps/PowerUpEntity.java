package edu.unh.cs.cs619.bulletzone.model.powerUps;

import edu.unh.cs.cs619.bulletzone.model.entities.FieldEntity;

public abstract class PowerUpEntity extends FieldEntity {
    int pos;
    PowerUpType type;

    @Override
    public abstract int getIntValue();

    /**
     *
     * @return new powerUpEntity
     */
    public abstract FieldEntity copy();

    @Override
    public abstract String toString();

    /**
     *
     * @return entity position
     */
    public int getPos() {
        return pos;
    }

    /**
     *
     * set entity pos
     */
    public void setPos(int pos) {
        this.pos = pos;
    }

    /**
     *
     * @return powerUpType
     */
    public PowerUpType getType() {
        return type;
    }
}
