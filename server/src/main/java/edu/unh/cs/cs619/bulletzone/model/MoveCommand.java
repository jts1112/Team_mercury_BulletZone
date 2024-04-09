package edu.unh.cs.cs619.bulletzone.model;

import static com.google.common.base.Preconditions.checkNotNull;

import edu.unh.cs.cs619.bulletzone.datalayer.terrain.Terrain;
import edu.unh.cs.cs619.bulletzone.model.events.MoveEvent;
import org.greenrobot.eventbus.EventBus;

import edu.unh.cs.cs619.bulletzone.model.events.TurnEvent;

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
     * @return returns True if Successes and False if Failure.
     * @throws TankDoesNotExistException
     */
    @Override
    public Boolean execute(Game game) throws TankDoesNotExistException {

        // Find tank
        Tank tank = game.getTanks().get(tankId);
        if (tank == null) {
            throw new TankDoesNotExistException(tankId);
        }

        // Check if the time from the last firing is too short
        long millis = System.currentTimeMillis();
        if(millis < tank.getLastMoveTime())
            return false;

        // Rotate tank if not moving forwards or backwards
        Direction currentDir = tank.getDirection();
        Direction desiredDirection = this.direction;
        int current = Byte.toUnsignedInt(Direction.toByte(currentDir));
        if (current == 0) {
            current = 8;
        }

        int desired = Byte.toUnsignedInt(Direction.toByte(desiredDirection));

        if (desired == ((current + 2) % 8) || desired == ((current - 2) % 8)) {
            // Set new direction
            tank.setDirection(desiredDirection);

            // Post new TurnEvent
            EventBus.getDefault().post(new TurnEvent(tank.getIntValue(), currentDir, tank.getDirection()));

            // Set the next valid move time
            tank.setLastMoveTime(millis + tank.getAllowedMoveInterval()); // TODO remove 10000
            return true;
        }

        FieldHolder parent = tank.getParent();

        FieldHolder nextField = parent.getNeighbor(direction);
        double difficulty = 1;
//        if (nextField.getEntity() instanceof Terrain) { // TODO uncomment later
//            Terrain terrain = (Terrain) nextField.getEntity();
//            if (tank.getIntValue() >= 10000000 && tank.getIntValue() < 20000000) { // its a normal tank
//                difficulty = terrain.getTankDifficulty();
//            } else if (tank.getIntValue() >= 20000000 && tank.getIntValue() < 30000000){ // its a miner
//                difficulty = terrain.getMinerDifficulty();
//            }
//        }

        checkNotNull(parent.getNeighbor(direction), "Neighbor is not available");
        boolean isCompleted;
        if (!nextField.isPresent()) {
            // If the next field is empty move the user

                /*try {
                    Thread.sleep(500);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }*/
//            if (difficulty > 0) { // TODO unccomment
                int oldPos = tank.getPosition();
                parent.clearField();
                nextField.setFieldEntity(tank);
                tank.setParent(nextField);
                int newPos = tank.getPosition();
                EventBus.getDefault().post(new MoveEvent(tank.getIntValue(), oldPos, newPos));

                isCompleted = true;
                tank.setLastMoveTime(millis + (long)(tank.getAllowedMoveInterval()*difficulty)); // TODO remove 1000
//            } else { // TODO uncomment
//                isCompleted = false;
//            }


        } else {
            isCompleted = false;
        }

        return isCompleted;
    }
}
