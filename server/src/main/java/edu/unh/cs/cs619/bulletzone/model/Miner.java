package edu.unh.cs.cs619.bulletzone.model;

public class Miner extends FieldEntity {
    private static final int INITIAL_LIFE = 120;
    private static final int BULLET_DAMAGE = 15;
    private static final int FIRE_INTERVAL = 1500; // 1.5 secs
    private static final int MOVE_INTERVAL = 750;

    private final long id;
    private final String ip;
    private int life;
    private Direction direction;
    private Dropship dropship;
    private long lastFireTime;
    private long lastMoveTime;

    public Miner(long id, Direction direction, String ip) {
        this.id = id;
        this.direction = direction;
        this.ip = ip;
        this.life = INITIAL_LIFE;
        this.lastFireTime = 0;
        this.lastMoveTime = 0;
    }

    @Override
    public int getIntValue() {
        return (int) (20_000_000 + (10_000 * id) + (10 * life) + Direction.toByte(direction));
    }

    @Override
    public FieldEntity copy() {
        return null;
    }

    //  getters and setters


    public long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Dropship getDropship() {
        return dropship;
    }

    public void setDropship(Dropship dropship) {
        this.dropship = dropship;
    }

    public long getLastFireTime() {
        return lastFireTime;
    }

    public void setLastFireTime(long lastFireTime) {
        this.lastFireTime = lastFireTime;
    }

    public long getLastMoveTime() {
        return lastMoveTime;
    }

    public void setLastMoveTime(long lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }
}
