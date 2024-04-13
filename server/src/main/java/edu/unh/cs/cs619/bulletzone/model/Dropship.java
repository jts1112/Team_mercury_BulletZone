package edu.unh.cs.cs619.bulletzone.model;

import java.util.ArrayList;
import java.util.List;

public class Dropship extends PlayableEntity {
    private static final int INITIAL_LIFE = 300;
    private static final int BULLET_DAMAGE = 50;
    private static final int FIRE_INTERVAL = 2000;
    private static final int ALLOWED_NUM_BULLETS = 1;
    private static final int MOVE_INTERVAL = -1;

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
        this.numberOfBullets = 0;
        this.lastFireTime = 0;
        this.lastMoveTime = 0;
        this.allowedMoveInterval = MOVE_INTERVAL;
        this.allowedFireInterval = FIRE_INTERVAL;
        this.allowedNumberOfBullets = ALLOWED_NUM_BULLETS;
        this.bulletDamage = BULLET_DAMAGE;
        this.tanks = new ArrayList<>();
        this.miners = new ArrayList<>();
    }

    @Override
    public FieldEntity copy() {
        return new Dropship(id, direction, ip);
    }

    @Override
    public void hit(int damage) {
        life -= damage;
        System.out.println("Dropship life: " + id + " : " + life);
    }

    public boolean fire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFireTime >= FIRE_INTERVAL) {
            lastFireTime = currentTime;
            return true;
        }
        return false;
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

    public int getNumMiners() {
        return numMiners;
    }

    public void setNumMiners(int numMiners) {
        this.numMiners = numMiners;
    }

    public int getNumTanks() {
        return numTanks;
    }

    public void setNumTanks(int numTanks) {
        this.numTanks = numTanks;
    }

    public List<Long> getMiners() {
        return miners;
    }

    public List<Long> getTanks() {
        return tanks;
    }
}
