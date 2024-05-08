package edu.unh.cs.cs619.bulletzone.model.commands;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.EntityDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;

public class TurnCommand implements Command {
    private long playableEntityId;
    private Direction direction;

    /**
     * TurnCommand Constructor To initialize variables needed to execute
     * @param playableEntityId The tankId of the tank that is turning.
     * @param direction The direction the tank is turning.
     */
    public  TurnCommand(long playableEntityId, Direction direction) {
        this.playableEntityId = playableEntityId;
        this.direction = direction;
    }

    /**
     * Runs code from turnMethod that was found in InMemoryGameRepository.
     * @return True if Success and False if Failure.
     * @throws EntityDoesNotExistException If Tank does not exist.
     */
    public Boolean execute(PlayableEntity playableEntity) throws EntityDoesNotExistException {
        if (playableEntity == null) {
            //Log.i(TAG, "Cannot find user with id: " + tankId);
            throw new EntityDoesNotExistException(playableEntityId);
        }

        long milliseconds = System.currentTimeMillis();
        if(milliseconds < playableEntity.getLastMoveTime())
            return false;

        playableEntity.setLastMoveTime(milliseconds+playableEntity.getAllowedMoveInterval());

            /*try {
                Thread.sleep(500);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }*/

        playableEntity.setDirection(direction);
        return true;
    }
}
