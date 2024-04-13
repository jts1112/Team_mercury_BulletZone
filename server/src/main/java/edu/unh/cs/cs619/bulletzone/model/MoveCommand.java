package edu.unh.cs.cs619.bulletzone.model;
import static com.google.common.base.Preconditions.checkNotNull;

import edu.unh.cs.cs619.bulletzone.model.events.MoveEvent;
import org.greenrobot.eventbus.EventBus;

import edu.unh.cs.cs619.bulletzone.model.events.TurnEvent;

public class MoveCommand implements Command {
    private long entityId;
    private Direction direction;

    /**
     * Move Command To initialize variables needed to execute
     * @param entityId passed in tankID
     * @param direction passed in Direction
     */
    public MoveCommand(long entityId, Direction direction){
        this.entityId = entityId;
        this.direction = direction;
    }

    /**
     * MoveCommand Execute to run code from move() that was in InMemoryGameRepository
     * @return returns True if Successes and False if Failure.
     * @throws TankDoesNotExistException if tank does not exist.
     */
    @Override
    public Boolean execute(PlayableEntity entity) throws TankDoesNotExistException {

        // Find tank
        if (entity == null) {
            throw new TankDoesNotExistException(entityId);
        }

        // Check if the time from the last firing is too short
        long millis = System.currentTimeMillis();
        if(millis < entity.getLastMoveTime())
            return false;

        // Rotate tank if not moving forwards or backwards
        Direction currentDir = entity.getDirection();
        Direction desiredDirection = this.direction;
        int current = Byte.toUnsignedInt(Direction.toByte(currentDir));
        if (current == 0) {
            current = 8;
        }

        int desired = Byte.toUnsignedInt(Direction.toByte(desiredDirection));

        if (desired == ((current + 2) % 8) || desired == ((current - 2) % 8)) {
            // Set new direction
            entity.setDirection(desiredDirection);

            // Post new TurnEvent
            EventBus.getDefault().post(new TurnEvent(entity.getIntValue(), currentDir,
                    entity.getDirection()));

            // Set the next valid move time
            entity.setLastMoveTime(millis + entity.getAllowedMoveInterval());
            return true;
        }

        FieldHolder parent = entity.getParent();

        FieldHolder nextField = parent.getNeighbor(direction);

        double difficulty = nextField.getTerrain().getDifficulty(entity); // TODO testing the difficulty

        checkNotNull(parent.getNeighbor(direction), "Neighbor is not available");
        boolean isCompleted;
        if (!nextField.isPresent()) {
            // If the next field is empty move the user

                int oldPos = entity.getPosition();
                parent.clearField();
                nextField.setFieldEntity(entity);
                entity.setParent(nextField);
                int newPos = entity.getPosition();
                EventBus.getDefault().post(new MoveEvent(entity.getIntValue(), oldPos, newPos));

                isCompleted = true;
                // TODO remove difficulty
                entity.setLastMoveTime(millis + (long)(entity.getAllowedMoveInterval()*difficulty));

        } else {
            isCompleted = false;
        }

        return isCompleted;
    }

}
