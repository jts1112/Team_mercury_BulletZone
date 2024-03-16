package edu.unh.cs.cs619.bulletzone.model;

public class TurnCommand implements Command{
    private long tankId;
    private  Direction direction;

    /**
     * TurnCommand Constructor To initialize variables needed to execute
     * @param tankId
     * @param direction
     */
    public  TurnCommand(long tankId,Direction direction) {
        this.tankId = tankId;
        this.direction = direction;
    }

    /**
     * TurnCommand execute class that runs code from turnMethod that was found in InMemoryGameRepository
     * @return True if Success and False if Failure.
     * @throws TankDoesNotExistException
     */
    public Boolean execute(Game game) throws TankDoesNotExistException {

        Tank tank = game.getTanks().get(tankId);
        if (tank == null) {
            //Log.i(TAG, "Cannot find user with id: " + tankId);
            throw new TankDoesNotExistException(tankId);
        }

        long millis = System.currentTimeMillis();
        if(millis < tank.getLastMoveTime())
            return false;

        tank.setLastMoveTime(millis+tank.getAllowedMoveInterval());

            /*try {
                Thread.sleep(500);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }*/

        tank.setDirection(direction);
        return true;
    }
}
