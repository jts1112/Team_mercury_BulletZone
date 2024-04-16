package edu.unh.cs.cs619.bulletzone.model.commands;

import edu.unh.cs.cs619.bulletzone.datalayer.terrain.Terrain;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;

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
        double resourceVal = terrain.getrescourceValue();



        return true;
    }
}
