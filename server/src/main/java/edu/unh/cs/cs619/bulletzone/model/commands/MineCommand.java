package edu.unh.cs.cs619.bulletzone.model.commands;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

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

        long fireTime = miner.getLastFireTime();
        long moveTime = miner.getLastMoveTime();
        if (miner.isDigging()) {
            return false;
        }
        miner.setDigging(true);
        Timer mineTimer = new Timer();
        mineTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized (monitor) {
                    if (miner.getLastFireTime() == fireTime && miner.getLastMoveTime() == moveTime) {
                        eventBus.post(new CreditEvent(resourceVal));
                    } else {
                        miner.setDigging(false);
                        mineTimer.cancel();
                        mineTimer.purge();
                    }
                }
            }
        }, 1000, 1000);
        return true;
    }

}
