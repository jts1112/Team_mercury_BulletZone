package edu.unh.cs.cs619.bulletzone.model.commands;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.EntityDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.Bullet;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Wall;
import edu.unh.cs.cs619.bulletzone.model.events.*;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

public class FireCommand implements Command {
    private final long playableEntityId;
    private int bulletType;
    private final Timer timer = new Timer();
    private static final int BULLET_PERIOD = 200;
    private final int[] bulletDamage = {15, 30, 50};
    private final int[] bulletDelay = {500, 1000, 1500};
    private final Object monitor;
    private final int dimension = 16;

    /**
     * Fire Command Method that initializes values needed for execution.
     * @param playableEntityId The ID of the entity that is firing
     * @param bulletType The type of bullet that is being fired
     */
    public FireCommand(long playableEntityId, int bulletType, Object monitor) {
        this.playableEntityId = playableEntityId;
        this.bulletType = bulletType;
        this.monitor = monitor;
    }

    /**
     * FireCommands execute method that runs code originally in fire() from InMemoryGameRepo
     * @return True if success False if Failure
     * @throws EntityDoesNotExistException If the entity does not exist
     */
    public Boolean execute(PlayableEntity playableEntity) throws EntityDoesNotExistException {
        // Find tank
        if (playableEntity == null) {
            throw new EntityDoesNotExistException(playableEntityId);
        }

        long milliseconds = System.currentTimeMillis();
        if(milliseconds < playableEntity.getLastFireTime()/*>tank.getAllowedFireInterval()*/) {
            return false;
        }

        Direction direction = playableEntity.getDirection();
        FieldHolder parent = playableEntity.getParent();

        if (!(bulletType>=1 && bulletType<=3)) {
            System.out.println("Bullet type must be 1, 2 or 3, set to 1 by default.");
            bulletType = 1;
        }

        playableEntity.setLastFireTime(milliseconds + bulletDelay[bulletType - 1]);

        final Bullet bullet = new Bullet(playableEntityId, direction, bulletDamage[bulletType-1]);

        // Set the same parent for the bullet.
        // This should be only a one way reference.
        bullet.setParent(parent);
        SpawnEvent bulletSpawnEvent = new SpawnEvent(bullet.getIntValue(), bullet.getPosition());
        EventBus.getDefault().post(bulletSpawnEvent);
        playableEntity.addBullet(bullet);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (monitor) {
                    if (!playableEntity.getBullets().contains(bullet)) {
                        cancel();
                        return;
                    }
                    System.out.println("Active Bullet: " + playableEntity.getNumberOfBullets() +
                            "---- Bullet ID: " + bullet.getIntValue());
                    FieldHolder currentField = bullet.getParent();
                    Direction direction = bullet.getDirection();
                    FieldHolder nextField = currentField.getNeighbor(direction);

                    // Is the bullet visible on the field?
                    boolean isVisible;

                    isVisible = currentField.isPresent() && (currentField.getEntity() == bullet);

                    if (nextField.isPresent()) {
                        // Something is there, hit it
                        FieldEntity nextEntity = nextField.getEntity();
                        int damageAmount = bullet.getDamage();
                        nextEntity.hit(damageAmount);

                        // Create new damageEvent
                        int position = nextEntity.getPosition();
                        int rawServerValue = nextEntity.getIntValue();
                        DamageEvent damageEvent = new DamageEvent(position, rawServerValue);
                        EventBus.getDefault().post(damageEvent);

                        if (nextField.getEntity() instanceof PlayableEntity playableEntity) {
                            System.out.println(playableEntity.getClass().getSimpleName()
                                    + " is hit, life: " + playableEntity.getLife());
                            if (playableEntity.getLife() <= 0) {
                                playableEntity.getParent().clearField();
                                playableEntity.setParent(null);
                                RemovalEvent removalEvent = new RemovalEvent(playableEntity.getPosition());
                                EventBus.getDefault().post(removalEvent);
                            }
                        } else if (nextField.getEntity() instanceof Wall wall) {
                            if ((wall.getIntValue() > 1000 && wall.getIntValue() <= 2000) || wall.getIntValue() == 6000) {
                                if (wall.getLife() <= 0) {
                                    wall.getParent().clearField();
                                    RemovalEvent removalEvent = new RemovalEvent(wall.getPos());
                                    EventBus.getDefault().post(removalEvent);
                                }
                            }
                        } else if (nextField.getEntity() instanceof PowerUpEntity powerUpEntity) {
                            powerUpEntity.getParent().clearField();
                            RemovalEvent removalEvent = new RemovalEvent(powerUpEntity.getPos());
                            EventBus.getDefault().post(removalEvent);
                            nextField.getTerrain().setPresentItem(0);
                        }
                        if (isVisible) {
                            currentField.clearField();
                            RemovalEvent removalEvent = new RemovalEvent(bullet.getPosition());
                            EventBus.getDefault().post(removalEvent);
                        }
                        playableEntity.removeBullet(bullet);
                        cancel();

                    } else {  // move bullet
                        if (isVisible) {
                            currentField.clearField();
                        }
                        int distanceTraveled = Math.abs(nextField.getPosition() - currentField.getPosition());
                        boolean enteringEdge = distanceTraveled > dimension || (distanceTraveled > 1 && distanceTraveled < dimension);
                        boolean enteringToughTerrain = nextField.getTerrain().getDifficulty(bullet) < 0;
                        if (enteringToughTerrain || enteringEdge) {
                            if (isVisible) {
                                currentField.clearField();
                                RemovalEvent removalEvent = new RemovalEvent(bullet.getPosition());
                                EventBus.getDefault().post(removalEvent);
                            }
                            playableEntity.removeBullet(bullet);
                            cancel();
                        } else {
                            int oldPos = bullet.getPosition();
                            nextField.setFieldEntity(bullet);
                            bullet.setParent(nextField);
                            int newPos = bullet.getPosition();
                            // Create new moveEvent
                            MoveEvent moveEvent = new MoveEvent(bullet.getIntValue(), oldPos, newPos);
                            EventBus.getDefault().post(moveEvent);
                        }
                    }
                }
            }
        }, 0, BULLET_PERIOD);

        return true;
    }

}
