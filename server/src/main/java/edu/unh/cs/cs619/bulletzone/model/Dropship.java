package edu.unh.cs.cs619.bulletzone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;


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
    private int numMiners;
    private int numTanks;

    private List<Long> miners;
    private List<Long> tanks;

    public Dropship(long id, Direction direction, String ip) {
        this.id = id;
        this.direction = direction;
        this.ip = ip;
        this.life = INITIAL_LIFE;
        this.numMiners = 1;
        this.numTanks = 1;
        numberOfBullets = 0;
        allowedNumberOfBullets = 1;
        lastFireTime = 0;
        allowedFireInterval = FIRE_INTERVAL;
        lastMoveTime = 0;
        allowedMoveInterval = -1;  // Cannot move

        this.miners = new ArrayList<>();
        this.tanks = new ArrayList<>();
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

    public void addMiner(long minerId) {
        miners.add(minerId);
    }

    public void removeMiner(long minerId) {
        miners.remove(minerId);
    }

    public void addTank_(long tankId) {
        tanks.add(tankId);
    }

    public void removeTank(long tankId) {
        tanks.remove(tankId);
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

    public void setLife(int life) {
        this.life = life;
    }

    public void setNumMiners(int numMiners) {
        this.numMiners = numMiners;
    }

    public void setNumTanks(int numTanks) {
        this.numTanks = numTanks;
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

    public int getNumMiners() {
        return numMiners;
    }

    public int getNumTanks() {
        return numTanks;
    }

    @Override
    public int getIntValue() {
        return (int) (30_000_000 + (10_000 * id) + (10 * life) + Direction.toByte(direction));
    }

    public List<Long> getMiners() {
        return miners;
    }

    public List<Long> getTanks() {
        return tanks;
    }

}
