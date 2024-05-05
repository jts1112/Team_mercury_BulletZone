package edu.unh.cs.cs619.bulletzone.repository;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;
import edu.unh.cs.cs619.bulletzone.model.IllegalTransitionException;
import edu.unh.cs.cs619.bulletzone.model.LimitExceededException;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.EntityDoesNotExistException;

public interface GameRepository {

    Dropship join(String ip);

    Game getGame();

    boolean move(long entityId, Direction direction)
            throws EntityDoesNotExistException, IllegalTransitionException, LimitExceededException;

    boolean fire(long tankId, int strength)
            throws EntityDoesNotExistException, LimitExceededException;

    void mine(long minerId) throws EntityDoesNotExistException;

    boolean dig(long playableEntityId) throws EntityDoesNotExistException;

    boolean ejectPowerUp(long playableEntityId) throws EntityDoesNotExistException;

    public void leave(long tankId)
            throws EntityDoesNotExistException;

    public void moveTo(long entityId, int targetX, int targetY) throws EntityDoesNotExistException, InterruptedException;

        // New ⬇️⬇️
    long spawnMiner(long dropshipId)
            throws EntityDoesNotExistException, LimitExceededException;

    long spawnTank(long dropshipId)
            throws EntityDoesNotExistException, LimitExceededException;
}
