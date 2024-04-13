package edu.unh.cs.cs619.bulletzone.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.greenrobot.eventbus.EventBus;

public class Tank extends FieldEntity implements Vehicle{

    private static final int INITIAL_LIFE = 100;
    private static final String TAG = "Tank";
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

    private final EventBus eventBus = EventBus.getDefault();

    public Tank(long id, Direction direction, String ip) {
        this.id = id;
        this.direction = direction;
        this.ip = ip;
        this.life = INITIAL_LIFE;
        numberOfBullets = 0;
        allowedNumberOfBullets = 2;
        lastFireTime = 0;
        allowedFireInterval = 1500;
        lastMoveTime = 0;
        allowedMoveInterval = 500;
    }

    @Override
    public FieldEntity copy() {
        return new Tank(id, direction, ip);
    }

    @Override
    public void hit(int damage) {
        life = life - damage;
        System.out.println("Tank life: " + id + " : " + life);
//		Log.d(TAG, "TankId: " + id + " hit -> life: " + life);

        if (life <= 0) {
//			Log.d(TAG, "Tank event");
            eventBus.post(Tank.this);
            //eventBus.post(new Object());
        }
    }


    @Override
    public String toString() {
        return "T";
    }

    // --------------------------------- Setters ---------------------------------
    public void setLastMoveTime(long lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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

    @Override
    public Boolean isWheeled() {
        return false;
    }

    @Override
    public Boolean isTracked() {
        return true;
    }
}
