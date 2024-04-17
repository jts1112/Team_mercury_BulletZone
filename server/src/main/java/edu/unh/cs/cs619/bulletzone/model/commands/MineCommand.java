package edu.unh.cs.cs619.bulletzone.model.commands;

import org.greenrobot.eventbus.EventBus;

import edu.unh.cs.cs619.bulletzone.datalayer.terrain.Terrain;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.events.CreditEvent;

public class MineCommand implements Command {

    private long minerId;
    private Object monitor;

    public MineCommand(long minerId, Object monitor) {
        this.minerId = minerId;
        this.monitor = monitor;
    }

    @Override
    public Boolean execute(PlayableEntity miner) throws TankDoesNotExistException {
        if (miner == null) {
            throw new TankDoesNotExistException(minerId);
        }

        FieldHolder parent = miner.getParent();
        Terrain terrain = parent.getTerrain();
        int resourceVal = (int) terrain.getrescourceValue();

        EventBus.getDefault().post(new CreditEvent(resourceVal));

        return true;
    }
}
