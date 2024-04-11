package edu.unh.cs.cs619.bulletzone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Dropship extends FieldEntity {
    private static final int INITIAL_LIFE= 300;
    private static final int BULLET_DAMAGE = 50;
    private static final int FIRE_INTERVAL = 2000; // 2 seconds
    private final long id;
    private final String ip;
    private long lastMoveTime;
    private int allowedMoveInterval;
    private long lastFireTime;
    private int allowedFireInterval;
    private int numberOfBullets;
    private int allowedNumberOfBullets;
    private int life;
    private Direction direction;

    public Dropship(long id, Direction direction, String ip) {
        this.id = id;
        this.direction = direction;
        this.ip = ip;
        this.life = INITIAL_LIFE;
        numberOfBullets = 0;
        allowedNumberOfBullets = 1;
        lastFireTime = 0;
        allowedFireInterval = FIRE_INTERVAL;
        lastMoveTime = 0;
        allowedMoveInterval = -1;  // Cannot move
    }

    @Override
    public FieldEntity copy() {
        return new Dropship(id, direction, ip);
    }

    @Override
    public void hit(int damage) {
        life = life - damage;
        System.out.println("Dropship life: " + id + " : " + life);
//		Log.d(TAG, "DropshipId: " + id + " hit -> life: " + life);

        if (life <= 0) {
//			Log.d(TAG, "Tank event");
            //eventBus.post(Tank.this);
            //eventBus.post(new Object());
        }
    }

    public boolean fire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFireTime >= FIRE_INTERVAL) {
            lastFireTime = currentTime;
            return true;
        }
        return false;
    }

    public boolean isDestroyed() {
        return life <= 0;
    }

    @Override
    public String toString() {
        return "D";
    }

    public Boolean isWheeled() {
        return false;
    }

    public Boolean isTracked() {
        return true;
    }

    // --------------------------------- Setters ---------------------------------
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public void setLastMoveTime(long lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }

    public void setAllowedMoveInterval(int allowedMoveInterval) {
        this.allowedMoveInterval = allowedMoveInterval;
    }

    public void setLastFireTime(long lastFireTime) {
        this.lastFireTime = lastFireTime;
    }

    public void setAllowedFireInterval(int allowedFireInterval) {
        this.allowedFireInterval = allowedFireInterval;
    }

    public void setNumberOfBullets(int numberOfBullets) {
        this.numberOfBullets = numberOfBullets;
    }

    public void setAllowedNumberOfBullets(int allowedNumberOfBullets) {
        this.allowedNumberOfBullets = allowedNumberOfBullets;
    }

    // --------------------------------- Getters ---------------------------------
    public long getLastMoveTime() {
        return lastMoveTime;
    }

    public long getAllowedMoveInterval() {
        return allowedMoveInterval;
    }

    public long getLastFireTime() {
        return lastFireTime;
    }

    public long getAllowedFireInterval() {
        return allowedFireInterval;
    }

    public int getNumberOfBullets() {
        return numberOfBullets;
    }

    public int getAllowedNumberOfBullets() {
        return allowedNumberOfBullets;
    }


    public Direction getDirection() {
        return direction;
    }

    public int getLife() {
        return life;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    public String getIp(){
        return ip;
    }

    @Override
    public int getIntValue() {
        return (int) (10_000_000 + (10_000 * id) + (10 * life) + Direction.toByte(direction));
    }
}
