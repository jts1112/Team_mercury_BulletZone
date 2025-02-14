package edu.unh.cs.cs619.bulletzone.model.entities;
import edu.unh.cs.cs619.bulletzone.model.Direction;

public class Bullet extends FieldEntity implements Vehicle{

    private long playableEntityId;
    private Direction direction;
    private int damage;

    public Bullet(long playableEntityId, Direction direction, int damage) {
        this.damage = damage;
        this.setOwner(playableEntityId);
        this.setDirection(direction);
    }

    @Override
    public int getIntValue() {
        return (int) (2_000_000 + 1000 * playableEntityId + damage * 10 + Direction.toByte(direction));
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public FieldEntity copy() {
        return new Bullet(playableEntityId, direction, damage);
    }

    public long getPlayableEntityId() {
        return playableEntityId;
    }

    public void setOwner(long playableEntityId) {
        this.playableEntityId = playableEntityId;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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
