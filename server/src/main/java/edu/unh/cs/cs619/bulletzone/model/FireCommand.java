package edu.unh.cs.cs619.bulletzone.model;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import edu.unh.cs.cs619.bulletzone.model.events.MoveEvent;
import edu.unh.cs.cs619.bulletzone.model.events.SpawnEvent;

public class FireCommand implements Command{
     private  long tankId;
     private int bulletType;
     private Game game;
    private final Timer timer = new Timer();
    private static final int BULLET_PERIOD = 200;
     private final int[] trackActiveBullets = {0, 0};
    private final int[] bulletDamage = {10, 30, 50};
    private final int[] bulletDelay = {500, 1000, 1500};
     private  Object monitor; // possibly needed

    /**
     * Fire Command Method that initializes values needed for execution.
     * @param tankId
     * @param bulletType
     */
    public FireCommand(long tankId, int bulletType){
        this.tankId = tankId;
        this.bulletType = bulletType;
    }

    /**
     * FireCommands execute method that runs code originally in fire() from InMemoryGameRepo
     * @return True if success False if Failure
     * @throws TankDoesNotExistException
     */
    public Boolean execute(Game game) throws TankDoesNotExistException {

        // Find tank
        Tank tank = game.getTanks().get(tankId);
        if (tank == null) {
            //Log.i(TAG, "Cannot find user with id: " + tankId);
            //return false;
            throw new TankDoesNotExistException(tankId);
        }

        if(tank.getNumberOfBullets() >= tank.getAllowedNumberOfBullets())
            return false;

        long millis = System.currentTimeMillis();
        if(millis < tank.getLastFireTime()/*>tank.getAllowedFireInterval()*/){
            return false;
        }

        //Log.i(TAG, "Cannot find user with id: " + tankId);
        Direction direction = tank.getDirection();
        FieldHolder parent = tank.getParent();
        tank.setNumberOfBullets(tank.getNumberOfBullets() + 1);

        if(!(bulletType>=1 && bulletType<=3)) {
            System.out.println("Bullet type must be 1, 2 or 3, set to 1 by default.");
            bulletType = 1;
        }

        tank.setLastFireTime(millis + bulletDelay[bulletType - 1]);

        int bulletId=0;
        if(trackActiveBullets[0]==0){
            bulletId = 0;
            trackActiveBullets[0] = 1;
        }else if(trackActiveBullets[1]==0){
            bulletId = 1;
            trackActiveBullets[1] = 1;
        }

        // Create a new bullet to fire
        final Bullet bullet = new Bullet(tankId, direction, bulletDamage[bulletType-1]);
        // Set the same parent for the bullet.
        // This should be only a one way reference.
        bullet.setParent(parent);
        bullet.setBulletId(bulletId);
        EventBus.getDefault().post(new SpawnEvent(bullet.getIntValue(), bullet.getPosition()));

        // TODO make it nicer
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                synchronized (monitor) {
                    System.out.println("Active Bullet: "+tank.getNumberOfBullets()+"---- Bullet ID: "+bullet.getIntValue());
                    FieldHolder currentField = bullet.getParent();
                    Direction direction = bullet.getDirection();
                    FieldHolder nextField = currentField
                            .getNeighbor(direction);

                    // Is the bullet visible on the field?
                    boolean isVisible = currentField.isPresent()
                            && (currentField.getEntity() == bullet);


                    if (nextField.isPresent()) {
                        // Something is there, hit it
                        nextField.getEntity().hit(bullet.getDamage());

                        if ( nextField.getEntity() instanceof  Tank){
                            Tank t = (Tank) nextField.getEntity();
                            System.out.println("tank is hit, tank life: " + t.getLife());
                            if (t.getLife() <= 0 ){
                                t.getParent().clearField();
                                t.setParent(null);
                                game.removeTank(t.getId());
                            }
                        }
                        else if ( nextField.getEntity() instanceof  Wall){
                            Wall w = (Wall) nextField.getEntity();
                            if (w.getIntValue() >1000 && w.getIntValue()<=2000 ){
                                game.getHolderGrid().get(w.getPos()).clearField();
                            }
                        }
                        if (isVisible) {
                            // Remove bullet from field
                            currentField.clearField();
                        }
                        trackActiveBullets[bullet.getBulletId()]=0;
                        tank.setNumberOfBullets(tank.getNumberOfBullets()-1);
                        cancel();

                    } else {
                        if (isVisible) {
                            // Remove bullet from field
                            currentField.clearField();
                        }

                        int oldPos = bullet.getPosition();
                        nextField.setFieldEntity(bullet);
                        bullet.setParent(nextField);
                        int newPos = bullet.getPosition();
                        EventBus.getDefault().post(new MoveEvent(bullet.getIntValue(), oldPos, newPos));
                    }
                }
            }
        }, 0, BULLET_PERIOD);

        return true;
    }
}
