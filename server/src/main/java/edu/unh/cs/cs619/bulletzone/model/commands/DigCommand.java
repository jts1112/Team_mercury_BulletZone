package edu.unh.cs.cs619.bulletzone.model.commands;

import org.greenrobot.eventbus.EventBus;

import edu.unh.cs.cs619.bulletzone.datalayer.terrain.Terrain;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.events.CreditEvent;

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

//        if (terrain.isEnterable()) {
//            new MoveCommand()
//        } else {
//            if (!entity.isWheeled()) {
//                return false;
//            }
//
//            entity.getAllowedMoveInterval()
//        }


        return true;
    }
}
