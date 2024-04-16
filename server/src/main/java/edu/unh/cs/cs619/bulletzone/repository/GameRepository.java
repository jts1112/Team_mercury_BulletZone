package edu.unh.cs.cs619.bulletzone.repository;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;
import edu.unh.cs.cs619.bulletzone.model.IllegalTransitionException;
import edu.unh.cs.cs619.bulletzone.model.LimitExceededException;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;

public interface GameRepository {

    Dropship join(String ip);

    Game getGame();

    boolean move(long entityId, Direction direction)
            throws TankDoesNotExistException, IllegalTransitionException, LimitExceededException;

    boolean fire(long tankId, int strength)
            throws TankDoesNotExistException, LimitExceededException;

    void mine(long minerId) throws TankDoesNotExistException;

    public void leave(long tankId)
            throws TankDoesNotExistException;

    public boolean moveTo(long entityId, int targetX, int targetY) throws TankDoesNotExistException, InterruptedException;

        // New ⬇️⬇️
    long spawnMiner(long dropshipId)
            throws TankDoesNotExistException, LimitExceededException;

    long spawnTank(long dropshipId)
            throws TankDoesNotExistException, LimitExceededException;
}
