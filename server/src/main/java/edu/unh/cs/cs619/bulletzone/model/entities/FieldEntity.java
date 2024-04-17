package edu.unh.cs.cs619.bulletzone.model.entities;

import edu.unh.cs.cs619.bulletzone.model.Direction;

public abstract class FieldEntity {
    protected FieldHolder parent;
    protected Direction direction;
    protected long lastMoveTime;
    protected int allowedMoveInterval;

    /**
     * Serializes the current {@link FieldEntity} instance.
     *
     * @return Integer representation of the current {@link FieldEntity}
     */
    public abstract int getIntValue();

    public FieldHolder getParent() {
        return parent;
    }

    public void setParent(FieldHolder parent) {
        this.parent = parent;
    }

    public int getPosition() {
        return parent.getPosition();
    }

    public abstract FieldEntity copy();

    public void hit(int damage) {
    }

    // Getters and Setters for direction, lastMoveTime, and allowedMoveInterval

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public long getLastMoveTime() {
        return lastMoveTime;
    }

    public void setLastMoveTime(long lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }

    public int getAllowedMoveInterval() {
        return allowedMoveInterval;
    }

    public void setAllowedMoveInterval(int allowedMoveInterval) {
        this.allowedMoveInterval = allowedMoveInterval;
    }
}
