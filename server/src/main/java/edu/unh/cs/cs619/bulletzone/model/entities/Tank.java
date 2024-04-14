package edu.unh.cs.cs619.bulletzone.model.entities;

import org.greenrobot.eventbus.EventBus;

import edu.unh.cs.cs619.bulletzone.model.Direction;

public class Tank extends PlayableEntity {
    private static final int INITIAL_LIFE = 100;
    private static final int ALLOWED_NUM_BULLETS = 2;
    private static final int BULLET_DAMAGE = 50;
    private static final int FIRE_INTERVAL = 500;
    private static final int MOVE_INTERVAL = 500;

    public Tank(long id, Direction direction, String ip) {
        this.id = id;
        this.direction = direction;
        this.ip = ip;
        this.life = INITIAL_LIFE;
        this.numberOfBullets = 0;
        this.lastFireTime = 0;
        this.lastMoveTime = 0;
        this.allowedMoveInterval = MOVE_INTERVAL;
        this.allowedFireInterval = FIRE_INTERVAL;
        this.allowedNumberOfBullets = ALLOWED_NUM_BULLETS;
        this.bulletDamage = BULLET_DAMAGE;
    }

    @Override
    public FieldEntity copy() {
        return new Tank(id, direction, ip);
    }

    @Override
    public void hit(int damage) {
        life -= damage;
        System.out.println("Tank life: " + id + " : " + life);
        if (life <= 0) {
            EventBus.getDefault().post(this);
        }
    }

    @Override
    public String toString() {
        return "T";
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
