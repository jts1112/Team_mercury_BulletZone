package edu.unh.cs.cs619.bulletzone.model.entities;

import edu.unh.cs.cs619.bulletzone.model.Direction;

public class Miner extends PlayableEntity{
    private static final int INITIAL_LIFE = 120;
    private static final int BULLET_DAMAGE = 15;
    private static final int FIRE_INTERVAL = 1500; // 1.5 secs
    private static final int MOVE_INTERVAL = 750;
    private static final int MINE_INTERVAL = 1000;
    private static final int ALLOWED_NUM_BULLETS = 1;
    private static final int HIT_DAMAGE = 25;

    private int mineInterval;
    private int lastMineTime;

    public Miner(long id, Direction direction, String ip) {
        this.id = id;
        this.ip = ip;
        this.direction = direction;
        this.life = INITIAL_LIFE;
        this.numberOfBullets = 0;
        this.lastFireTime = 0;
        this.lastMoveTime = 0;
        this.lastMineTime = 0;
        this.mineInterval = MINE_INTERVAL;
        this.allowedMoveInterval = MOVE_INTERVAL;
        this.allowedFireInterval = FIRE_INTERVAL;
        this.allowedNumberOfBullets = ALLOWED_NUM_BULLETS;
        this.bulletDamage = BULLET_DAMAGE;
        this.hitDamage = HIT_DAMAGE;
    }

    @Override
    public String toString() {
        return "M";
    }

    @Override
    public FieldEntity copy() {
        return new Miner(id, direction, ip);
    }

    @Override
    public int getIntValue() {
        return (int) (20_000_000 + (10_000 * id) + (10 * life) + Direction.toByte(direction));
    }

    public int getMineInterval() {
        return mineInterval;
    }

    public void setMineInterval(int mineInterval) {
        this.mineInterval = mineInterval;
    }

    public int getLastMineTime() {
        return lastMineTime;
    }

    public void setLastMineTime(int lastMineTime) {
        this.lastMineTime = lastMineTime;
    }

    @Override
    public Boolean isWheeled() {
        return true;
    }

    @Override
    public Boolean isTracked() {
        return false;
    }
}
