package edu.unh.cs.cs619.bulletzone.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.unh.cs.cs619.bulletzone.datalayer.item.GameItemRepository;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.powerUps.*;
import edu.unh.cs.cs619.bulletzone.model.events.RemovalEvent;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;

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
    protected boolean hasActionQueued = false;

    public void setId(long id) {
        this.id = id;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public void setBulletDamage(int bulletDamage) {
        this.bulletDamage = bulletDamage;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void removeBullet(Bullet bullet) {
        if (bullets.remove(bullet)) {
            numberOfBullets = Math.max(0, numberOfBullets - 1);
        }
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        numberOfBullets++;
        System.out.println("Num bullets: " + numberOfBullets + " allowed: " + allowedNumberOfBullets);
        if (numberOfBullets > allowedNumberOfBullets) {
            System.out.println("Num bullets: " + numberOfBullets + " allowed: " + allowedNumberOfBullets);
            Bullet oldestBullet = bullets.remove(0);
            FieldHolder currentField = oldestBullet.getParent();
            if (currentField.isPresent() && currentField.getEntity() == oldestBullet) {
                currentField.clearField();
                RemovalEvent removalEvent = new RemovalEvent(oldestBullet.getPosition());
                EventBus.getDefault().post(removalEvent);
            }
            numberOfBullets--;
        }
    }

    protected List<Bullet> bullets = new ArrayList<>();

    protected PowerUpComponent powerUp = new ConcretePowerUpComponent();
    protected  GameItemRepository repo = new GameItemRepository();

    // Getters and Setters
    public long getLastMoveTime() {
        return lastMoveTime;
    }

    public void setLastMoveTime(long lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }

    public int getAllowedMoveInterval() {
        return powerUp.getMovementInterval(allowedMoveInterval);
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
        return powerUp.getFireInterval(allowedFireInterval);
    }

    public void setAllowedFireInterval(int allowedFireInterval) {
        this.allowedFireInterval = allowedFireInterval;
    }

    public int getNumberOfBullets() {
        return numberOfBullets;
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

    public boolean isDestroyed() {
        return life <= 0;
    }

    public void pickupPowerUp(PowerUpType type) {
        switch (type) {
            case AntiGrav -> powerUp = new AntiGravPowerUp(powerUp);
            case FusionReactor -> powerUp = new FusionReactorPowerUp(powerUp);
        }
    }

    public Optional<PowerUpEntity> dropPowerUp() {
        PowerUpComponent temp = powerUp;
        Optional<PowerUpComponent> prev = powerUp.getPrevPowerUp();
        if (prev.isEmpty()) {
            return Optional.empty();
        } else {
            powerUp = prev.get();
            Optional<PowerUpType> type = temp.getPowerUpType();
            if (type.isPresent()) {
                switch (type.get()) {
                    case AntiGrav:
                        return Optional.of(new AntiGravPowerUpEntity());
                    case FusionReactor:
                        return Optional.of(new FusionReactorPowerUpEntity());
                    case Thingamajig:
                        // bank stuff here
                        return Optional.empty();
                }
            }
            return Optional.empty();
        }
    }

    public void hit(int damage) {
        life -= damage;
        System.out.println("Tank life: " + id + " : " + life);
        if (life <= 0) {
            EventBus.getDefault().post(this);
        }
    }

    public boolean isHasActionQueued() {
        return hasActionQueued;
    }
}
