package edu.unh.cs.cs619.bulletzone.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.unh.cs.cs619.bulletzone.model.Direction;

public abstract class PlayableEntity extends FieldEntity implements Vehicle{
    protected long id;
    protected long lastMoveTime;
    protected long lastFireTime;
    protected int allowedMoveInterval;
    protected int allowedFireInterval;
    protected int numberOfBullets;
    protected int allowedNumberOfBullets;
    protected int life;
    protected int bulletDamage;
    protected String ip;
    protected Direction direction;

    // Getters and Setters
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

    public long getLastFireTime() {
        return lastFireTime;
    }

    public void setLastFireTime(long lastFireTime) {
        this.lastFireTime = lastFireTime;
    }

    public int getAllowedFireInterval() {
        return allowedFireInterval;
    }

    public void setAllowedFireInterval(int allowedFireInterval) {
        this.allowedFireInterval = allowedFireInterval;
    }

    public int getNumberOfBullets() {
        return numberOfBullets;
    }

    public void setNumberOfBullets(int numberOfBullets) {
        this.numberOfBullets = numberOfBullets;
    }

    public int getAllowedNumberOfBullets() {
        return allowedNumberOfBullets;
    }

    public void setAllowedNumberOfBullets(int allowedNumberOfBullets) {
        this.allowedNumberOfBullets = allowedNumberOfBullets;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

//    @Override
//    public int getIntValue() {
//        return (int) (30_000_000 + (10_000 * id) + (10 * life) + Direction.toByte(direction));
//    }

    public boolean isDestroyed() {
        return life <= 0;
    }
}
