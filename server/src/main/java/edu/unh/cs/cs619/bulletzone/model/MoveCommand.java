package edu.unh.cs.cs619.bulletzone.model;

import static com.google.common.base.Preconditions.checkNotNull;

import org.greenrobot.eventbus.EventBus;

import edu.unh.cs.cs619.bulletzone.model.events.MoveEvent;

public class MoveCommand implements Command{
    private long tankId;
    private Direction direction;

    /**
     * Move Command To initialize variables needed to execute
     * @param tankId passed in tankID
     * @param direction passed in Direction
     */
    public MoveCommand(long tankId,Direction direction){
        this.tankId = tankId;
        this.direction = direction;
    }

    /**
     * MoveCommand Execute to run code from move() that was in InMemoryGameRepository
     * @return returns True if Successs and False if Failure.
     * @throws TankDoesNotExistException
     */
    @Override
    public Boolean execute(Game game) throws TankDoesNotExistException {

        // Find tank
        Tank tank = game.getTanks().get(tankId);
        if (tank == null) {
            //Log.i(TAG, "Cannot find user with id: " + tankId);
            //return false;
            throw new TankDoesNotExistException(tankId);
        }

        long millis = System.currentTimeMillis();
        if(millis < tank.getLastMoveTime())
            return false;

        tank.setLastMoveTime(millis + tank.getAllowedMoveInterval());

        FieldHolder parent = tank.getParent();

        FieldHolder nextField = parent.getNeighbor(direction);
        checkNotNull(parent.getNeighbor(direction), "Neightbor is not available");

        boolean isCompleted;
        if (!nextField.isPresent()) {
            // If the next field is empty move the user

                /*try {
                    Thread.sleep(500);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }*/

            int oldPos = tank.getPosition();
            parent.clearField();
            nextField.setFieldEntity(tank);
            tank.setParent(nextField);
            int newPos = tank.getPosition();
            EventBus.getDefault().post(new MoveEvent(tank.getIntValue(), oldPos, newPos));

            isCompleted = true;
        } else {
            isCompleted = false;
        }

        return isCompleted;
    }
}
