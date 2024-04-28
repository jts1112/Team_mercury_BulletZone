package edu.unh.cs.cs619.bulletzone.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.unh.cs.cs619.bulletzone.datalayer.item.GameItemRepository;
import edu.unh.cs.cs619.bulletzone.model.events.CreditEvent;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.powerUps.*;
import edu.unh.cs.cs619.bulletzone.model.events.RemovalEvent;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.unh.cs.cs619.bulletzone.repository.InMemoryGameRepository;
import org.greenrobot.eventbus.EventBus;

public abstract class  PlayableEntity extends FieldEntity implements Vehicle{
    protected long id;
    protected long lastMoveTime;
    protected long lastFireTime;
    protected int allowedMoveInterval;
    protected int allowedFireInterval;
    protected int numberOfBullets;
    protected int allowedNumberOfBullets;
    protected int life;
    protected int maxLife;
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

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
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
        int interval = powerUp.getMovementInterval(allowedMoveInterval);
        System.out.println("move interval: " + interval);
        return interval;
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
        int interval = powerUp.getFireInterval(allowedFireInterval);
        System.out.println("fire interval: " + interval);
        return interval;
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

    public PowerUpComponent getPowerUps() {
        return powerUp;
    }

    public boolean isDestroyed() {
        return life <= 0;
    }

    public void pickupPowerUp(PowerUpType type) {
        Random random = new Random();
        if (type != null) {
            System.out.println("picked up " + type);
            switch (type) {
                case AntiGrav -> powerUp = new AntiGravPowerUp(powerUp);
                case FusionReactor -> powerUp = new FusionReactorPowerUp(powerUp);
                case Thingamajig -> EventBus.getDefault().post(new CreditEvent(random.nextInt(100) + 50)); // give a random number amount of credits that averages 100
                case PoweredDrill -> powerUp = new PoweredDrillPowerUp(powerUp);
                case DeflectorShield -> powerUp = new DeflectorShieldPowerUp(powerUp, this);
                case AutomatedRepairKit -> powerUp = new AutomatedRepairKitPowerUp(powerUp, this);
            }
        } else {
            System.out.println("PowerUpType is null, unable to pickup");
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
                    case PoweredDrill:
                        return Optional.of(new PoweredDrillPowerUpEntity());
                    case DeflectorShield:
                        return Optional.of(new DeflectorShieldPowerUpEntity());
                    case Thingamajig, AutomatedRepairKit:
                        return Optional.empty();
                }
            }
            return Optional.empty();
        }
    }

    public void hit(int damage) {
        int final_damage = powerUp.getModifiedDamageToHolder(new DamageTuple<>(damage, false)).damage;
        life -= final_damage;
        System.out.println("Tank life: " + id + " : " + life);
        if (life <= 0) {
            EventBus.getDefault().post(this);
        }
    }

    public boolean isHasActionQueued() {
        return hasActionQueued;
    }
}
