package edu.unh.cs.cs619.bulletzone.model.commands;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import edu.unh.cs.cs619.bulletzone.datalayer.terrain.EntranceTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.Terrain;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.events.CreditEvent;
import edu.unh.cs.cs619.bulletzone.model.events.DamageEvent;
import edu.unh.cs.cs619.bulletzone.model.events.EntranceEvent;
import edu.unh.cs.cs619.bulletzone.model.events.RemovalEvent;
import jdk.jfr.Event;

public class DigCommand implements Command {

    private long entityId;
    private Object monitor;

    public DigCommand(long entityId, Object monitor) {
        this.entityId = entityId;
        this.monitor = monitor;
    }

    @Override
    public Boolean execute(PlayableEntity entity) throws TankDoesNotExistException {
        if (entity == null) {
            throw new TankDoesNotExistException(entityId);
        }

        FieldHolder parent = entity.getParent();
        Terrain terrain = parent.getTerrain();

        if (terrain.isEnterable()) {
            // New move through the entrance
            MoveCommand move = new MoveCommand(entityId, terrain.getDirection());
            return move.execute(entity);
        } else {
            if (!entity.isWheeled()) {
                return false;
            }

            if (entity.isDigging()) { // dig command while digging is running
                return false;
            }

            if (entity.getPosition() > 511) { // Can't dig deeper than third layer
                return false;
            }

            FieldHolder under = parent.getNeighbor(Direction.Below);

            if (under.getEntity() != null)
                if (under.getEntity().getIntValue() == 5000
                        || under.getEntity().getIntValue() > 6000) {
                    // hitting a rock or playable entity and not diggable
                    return false;
                }

            if (under.getEntity() == null || under.getEntity().getIntValue() == 0) { // no dirt so instant entrance made
                parent.setTerrain(new EntranceTerrain(Direction.Below));
                under.setTerrain(new EntranceTerrain(Direction.Above));
                EventBus.getDefault().post(new EntranceEvent(parent.getTerrain().getIntValue(), parent.getPosition()));
                EventBus.getDefault().post(new EntranceEvent(under.getTerrain().getIntValue(), under.getPosition()));
                return true;
            } else {
                long fireTime = entity.getLastFireTime();
                long moveTime = entity.getLastMoveTime();
                Timer digTimer = new Timer();
                digTimer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        synchronized (monitor) {
                            if (entity.getLastFireTime() == fireTime && entity.getLastMoveTime() == moveTime
                            && entity.getLife() > 0) {
                                entity.setDigging(true);
                                entity.hit(entity.getSelfHitDamage());
                                EventBus.getDefault().post(new DamageEvent(entity.getPosition(), entity.getIntValue()));

                                if (entity.getLife() <= 0) {
                                    entity.getParent().clearField();
                                    entity.setParent(null);

                                    // Create new removalEvent
                                    RemovalEvent removalEvent = new RemovalEvent(entity.getPosition());
                                    EventBus.getDefault().post(removalEvent);
                                }

                                under.getEntity().hit(entity.getHitDamage());
                                EventBus.getDefault().post(new DamageEvent(under.getPosition(), under.getEntity().getIntValue()));

                                if (under.getEntity().getIntValue() <= 5000) {
                                    under.clearField();

                                    EventBus.getDefault().post(new RemovalEvent(under.getPosition()));

                                    parent.setTerrain(new EntranceTerrain(Direction.Below));
                                    under.setTerrain(new EntranceTerrain(Direction.Above));
                                    // Create new EntranceEvents
                                    EventBus.getDefault().post(new EntranceEvent(parent.getTerrain().getIntValue(), parent.getPosition()));
                                    EventBus.getDefault().post(new EntranceEvent(under.getTerrain().getIntValue(), under.getPosition()));

                                    entity.setDigging(false);
                                    digTimer.cancel();
                                    digTimer.purge();
                                }
                            } else {
                                entity.setDigging(false);
                                digTimer.cancel();
                                digTimer.purge();
                            }
                        }
                    }
                }, 0, entity.getAllowedMoveInterval());
            }
        }
        return true;
    }
}
