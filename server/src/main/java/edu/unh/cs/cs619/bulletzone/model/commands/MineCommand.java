package edu.unh.cs.cs619.bulletzone.model.commands;

import org.greenrobot.eventbus.EventBus;

import edu.unh.cs.cs619.bulletzone.datalayer.terrain.Terrain;
import edu.unh.cs.cs619.bulletzone.model.EntityDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.events.CreditEvent;

public class MineCommand implements Command {

    private long minerId;
    private Object monitor;
    private EventBus eventBus; // For Miner Tests only not used in actuall code.

    public MineCommand(long minerId, Object monitor) {
        this.minerId = minerId;
        this.monitor = monitor;
        this.eventBus = EventBus.getDefault();
    }

    /**
     * This method is specifically for testing to allow use of event bus within the tests.
     * @param minerId
     * @param monitor
     * @param eventBus
     */
    public MineCommand(long minerId, Object monitor, EventBus eventBus) {
        this.minerId = minerId;
        this.monitor = monitor;
        this.eventBus = eventBus;
    }

    @Override
    public Boolean execute(PlayableEntity miner) throws EntityDoesNotExistException {
        if (miner == null) {
            throw new EntityDoesNotExistException(minerId);
        }

        FieldHolder parent = miner.getParent();
        Terrain terrain = parent.getTerrain();
        System.out.println(terrain.getrescourceValue());
        int resourceVal = (int) terrain.getrescourceValue();

        eventBus.post(new CreditEvent(resourceVal));
        return true;
    }


    // Have a seem method with the
}
