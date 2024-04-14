package edu.unh.cs.cs619.bulletzone.model.commands;

import edu.unh.cs.cs619.bulletzone.datalayer.terrain.ForestTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.RockyTerrain;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.Bullet;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
import edu.unh.cs.cs619.bulletzone.model.entities.Wall;
import edu.unh.cs.cs619.bulletzone.model.events.*;
import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

public class FireCommand implements Command {
    private  long playableEntityId;
    private int bulletType;
    private final Timer timer = new Timer();
    private static final int BULLET_PERIOD = 200;
    private final int[] trackActiveBullets = {0, 0};
    private final int[] bulletDamage = {10, 30, 50};
    private final int[] bulletDelay = {500, 1000, 1500};
    private final Object monitor; // possibly needed

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
     * @throws TankDoesNotExistException If the entity does not exist
     */
    public Boolean execute(PlayableEntity playableEntity) throws TankDoesNotExistException {

        // Find tank
        if (playableEntity == null) {
            throw new TankDoesNotExistException(playableEntityId);
        }

        if(playableEntity.getNumberOfBullets() >= playableEntity.getAllowedNumberOfBullets())
            return false;

        long milliseconds = System.currentTimeMillis();
        if(milliseconds < playableEntity.getLastFireTime()/*>tank.getAllowedFireInterval()*/) {
            return false;
        }

        Direction direction = playableEntity.getDirection();
        FieldHolder parent = playableEntity.getParent();
        playableEntity.setNumberOfBullets(playableEntity.getNumberOfBullets() + 1);

        if(!(bulletType>=1 && bulletType<=3)) {
            System.out.println("Bullet type must be 1, 2 or 3, set to 1 by default.");
            bulletType = 1;
        }

        playableEntity.setLastFireTime(milliseconds + bulletDelay[bulletType - 1]);

        int bulletId=0;
        if(trackActiveBullets[0]==0){
            bulletId = 0;
            trackActiveBullets[0] = 1;
        }else if(trackActiveBullets[1]==0){
            bulletId = 1;
            trackActiveBullets[1] = 1;
        }

        final Bullet bullet = new Bullet(playableEntityId, direction, bulletDamage[bulletType-1]);
        // Set the same parent for the bullet.
        // This should be only a one way reference.
        bullet.setParent(parent);
        bullet.setBulletId(bulletId);
        EventBus.getDefault().post(new SpawnEvent(bullet.getIntValue(), bullet.getPosition()));

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (monitor) {
                    System.out.println("Active Bullet: " + playableEntity.getNumberOfBullets() +
                            "---- Bullet ID: "+bullet.getIntValue());
                    FieldHolder currentField = bullet.getParent();
                    Direction direction = bullet.getDirection();
                    FieldHolder nextField = currentField.getNeighbor(direction);

                    // Is the bullet visible on the field?
                    boolean isVisible;

                    isVisible = currentField.isPresent() && (currentField.getEntity() == bullet);

                    if (nextField.isPresent()) {
                        // Something is there, hit it
                        FieldEntity entity = nextField.getEntity();
                        int damageAmount = bullet.getDamage();
                        entity.hit(damageAmount);

                        // Create new damageEvent
                        int position = entity.getPosition();
                        DamageEvent damageEvent = new DamageEvent(position, entity.getIntValue());
                        EventBus.getDefault().post(damageEvent);

                        if (nextField.getEntity() instanceof Tank t){
                            System.out.println("tank is hit, tank life: " + t.getLife());
                            if (t.getLife() <= 0 ){
                                t.getParent().clearField();
                                t.setParent(null);

                                // Create new removalEvent
                                RemovalEvent removalEvent = new RemovalEvent(t.getPosition());
                                EventBus.getDefault().post(removalEvent);
                            }
                        } else if (nextField.getEntity() instanceof Wall w ) {
                            // Check if the wall is destructible
                            if (w.getIntValue() > 1000 && w.getIntValue() <= 2000) {
                                if (w.getLife() <= 0) {  // If 0 health
                                    w.getParent().clearField();

                                    // Create new RemovalEvent
                                    RemovalEvent removalEvent = new RemovalEvent(w.getPos());
                                    EventBus.getDefault().post(removalEvent);
                                }
                            }
                        }
                        if (isVisible) {
                            // Remove bullet from field
                            currentField.clearField();
                            RemovalEvent removalEvent = new RemovalEvent(bullet.getPosition());
                            EventBus.getDefault().post(removalEvent);
                        }
                        trackActiveBullets[bullet.getBulletId()]=0;
                        playableEntity.setNumberOfBullets(playableEntity.getNumberOfBullets()-1);
                        cancel();

                    } else {  // Nothing is there, move bullet along
                        if (isVisible) {
                            // Remove bullet from field
                            currentField.clearField();
                        }

//                        // bullets cant enter forest so destroy bullet.
                        if (nextField.getTerrain().getDifficulty(bullet) < 0) {
                            if (isVisible) {
                                // Remove bullet from field
                                currentField.clearField();
                                RemovalEvent removalEvent = new RemovalEvent(bullet.getPosition());
                                EventBus.getDefault().post(removalEvent);
                            }
                            trackActiveBullets[bullet.getBulletId()]=0;
                            playableEntity.setNumberOfBullets(playableEntity.getNumberOfBullets()-1);
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
