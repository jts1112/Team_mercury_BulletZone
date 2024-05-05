package edu.unh.cs.cs619.bulletzone.repository;


//import android.health.connect.datatypes.units.Power;

import edu.unh.cs.cs619.bulletzone.datalayer.user.GameUser;
import edu.unh.cs.cs619.bulletzone.model.commands.*;

import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.entities.AntiGravPowerUpEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.LimitExceededException;
import edu.unh.cs.cs619.bulletzone.model.entities.FusionReactorPowerUpEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Miner;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.ThingamajigEntity;
import edu.unh.cs.cs619.bulletzone.model.events.SpawnEvent;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import edu.unh.cs.cs619.bulletzone.util.LogUtil;

@Component
public class InMemoryGameRepository implements GameRepository {

    private static final int FIELD_DIM = 16;


    private final Timer timer = new Timer();
    private final AtomicLong idGenerator = new AtomicLong();
    private final Object monitor = new Object();
    private Game game = null;

    private  CommandPattern commands = null;
    private final DataRepository data;
    private static final Logger log = LoggerFactory.getLogger(InMemoryGameRepository.class);

    public InMemoryGameRepository() {
        this.data = DataRepositoryFactory.getInstance();
    }
    /**
     * Generates a new tank to join the game.
     * @param ip IP address of the tank.
     * @return Generated Tank object.
     */
    @Override
    public Dropship join(String ip) {
        synchronized (this.monitor) {
            Dropship dropship;
            if (game == null) {
                this.create();
            }

            // Check if a dropship with the same IP already exists in the game.
            Dropship existingDropship = game.getDropship(ip);
            if (existingDropship != null) {
                return existingDropship;
            }

            long dropshipId = this.idGenerator.getAndIncrement();

            dropship = new Dropship(dropshipId, Direction.Up, ip);

            Random random = new Random();
            int x;
            int y;

            // This may run for forever.. If there is no free space. XXX
            for (; ; ) {
                x = random.nextInt(FIELD_DIM);
                y = random.nextInt(FIELD_DIM);
                // FieldHolder fieldElement = game.getHolderGrid().get(x * FIELD_DIM + y);
                FieldHolder fieldElement = game.getHolderGrid().get(x * FIELD_DIM + y);
                if (!fieldElement.isPresent()) {
                    fieldElement.setFieldEntity(dropship);
                    dropship.setParent(fieldElement);
                    break;
                }
            }
            game.addDropship(dropship);
            return dropship;
        }
    }

    @Override
    public Game getGame() {
        synchronized (this.monitor) {
            if (game == null) {
                this.create();
            }
        }
        return game;
    }

    @Override
    public boolean move(long entityId, Direction direction) throws TankDoesNotExistException {
        synchronized (this.monitor) {
            PlayableEntity playableEntity = game.getPlayableEntity(entityId);

            System.out.println("Moving entity: " + entityId);
            System.out.println("Entity type: " + playableEntity.getClass().getSimpleName());

            MoveCommand moveCommand = new MoveCommand(entityId, direction);
            boolean moveResult = moveCommand.execute(playableEntity);

            System.out.println("Move result: " + moveResult);

            return moveResult;
        }
    }

    public void moveTo(long entityId, int targetX, int targetY) throws TankDoesNotExistException, InterruptedException {

        if (game.getDropship(entityId) != null) {
            return ;
        }
        if (targetX < 0 || targetX >= FIELD_DIM || targetY < 0 || targetY >= FIELD_DIM) {
            return ;
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    synchronized (monitor) {
                        PlayableEntity playableEntity = game.getPlayableEntity(entityId);
                        if (playableEntity == null) {
                            throw new TankDoesNotExistException(entityId);
                        }

                        if (commands == null){
                            commands = new CommandPattern(game);
                        }

                        FieldHolder currentField = playableEntity.getParent();
                        int currentPosition = currentField.getPosition();
                        int currentX = currentPosition % FIELD_DIM;
                        int currentY = currentPosition / FIELD_DIM;

//                        PlayableEntity playableEntity = game.getPlayableEntity(entityId); // similar was already declared above
                        int sleepTime = playableEntity.getAllowedMoveInterval() * 2;

                        // if current x is less than then go left. else go right.
                        // find direction need to move

                        Direction directionX = currentX < targetX ? Direction.Right : Direction.Left;
                        int initialX = playableEntity.getPosition() % FIELD_DIM;
                        // add move command in the direction.
                        if (playableEntity.getDirection() != directionX) { // if not facing the direction needed need to turn that direction
                            commands.addMoveCommand(entityId, directionX);
                        }
                        // while absolute value of difference
                        for (int xposition = 0; xposition < Math.abs(targetX - currentX); xposition++) { // add commands to x direction.
                            commands.addMoveCommand(entityId, directionX);
                        }

                        // start doing y commands
                        Direction directionY = currentY < targetY ? Direction.Down : Direction.Up;
                        int initialY = (int) Math.floor((double) playableEntity.getPosition() / FIELD_DIM);
                        if (directionX != directionY) { // if not facing the direction needed to turn that directoin face that direction
                            commands.addMoveCommand(entityId, directionY);
                        }
                        for (int yposition = 0; yposition < Math.abs(targetY - currentY); yposition++) {
                            commands.addMoveCommand(entityId, directionY);
                        }
                        commands.executeCommands(sleepTime, playableEntity);
                    }
                } catch (InterruptedException | TankDoesNotExistException e) {
                    log.error("Error during moveTo operation", e);
                }
            }
        }, 0);

    }

    @Override
    public boolean fire(long playableEntityId, int bulletType) throws TankDoesNotExistException {
        synchronized (this.monitor) {
            FireCommand fireCommand = new FireCommand(playableEntityId, bulletType, this.monitor);
            PlayableEntity playableEntity = game.getPlayableEntity(playableEntityId);
            return fireCommand.execute(playableEntity);
        }
    }

    @Override
    public void mine(long minerId) throws TankDoesNotExistException {
        PlayableEntity miner = game.getMiner(minerId);
        long fireTime = miner.getLastFireTime();
        long moveTime = miner.getLastMoveTime();
        Timer mineTimer = new Timer();
        mineTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized (monitor) {
                    if (miner.getLastFireTime() == fireTime && miner.getLastMoveTime() == moveTime) {
                        MineCommand mineCommand = new MineCommand(minerId, monitor);
                        try {
                            mineCommand.execute(miner);
                        } catch (TankDoesNotExistException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        mineTimer.cancel();
                        mineTimer.purge();
                    }
                }
            }
        }, 1000, 1000);
    }

    @Override
    public boolean dig(long playableEntityId) throws TankDoesNotExistException {
        PlayableEntity playableEntity = game.getPlayableEntity(playableEntityId);
        DigCommand dig = new DigCommand(playableEntityId, monitor);
        return dig.execute(playableEntity);
    }

    @Override
    public boolean ejectPowerUp(long playableEntityId) throws TankDoesNotExistException {
        EjectPowerUpCommand ejectPowerUpCommand = new EjectPowerUpCommand(playableEntityId);
        PlayableEntity playableEntity = game.getPlayableEntity(playableEntityId);
        game.incrementnumPowerups();
        boolean ejectResult = ejectPowerUpCommand.execute(playableEntity);
        if (ejectResult) {
            game.incrementnumPowerups();
        }

        return ejectResult;
    }

    @Override
    public void leave(long dropshipId) throws TankDoesNotExistException {
        synchronized (this.monitor) {
            int totalPowerupValue = 0;

            if (!this.game.getDropships().containsKey(dropshipId)) {
                throw new TankDoesNotExistException(dropshipId);
            }

            System.out.println("leave() called, dropship ID: " + dropshipId);

            // remove dropship and sell its power-ups
            Dropship dropship = game.getDropship(dropshipId);
            FieldHolder parent = dropship.getParent();
            parent.clearField();
            game.removeDropship(dropshipId);

            totalPowerupValue = dropship.getPowerUps().getPowerUpValue(totalPowerupValue);

            // remove all tanks for a dropship and sell their power-ups
            List<Long> tankIDs = dropship.getTankIds();
            for (long tankId : tankIDs) {
                Tank tank = game.getTank(tankId);
                tank.getParent().clearField();
                game.removeTank(tankId);

                totalPowerupValue = tank.getPowerUps().getPowerUpValue(totalPowerupValue);
            }

            // remove all miners for a dropship and sell their power-ups
            List<Long> minerIDs = dropship.getMinerIds();
            for (long minerId : minerIDs) {
                Miner miner = game.getMiner(minerId);
                miner.getParent().clearField();
                game.removeMiner(minerId);

                totalPowerupValue = miner.getPowerUps().getPowerUpValue(totalPowerupValue);
            }
            GameUser user = data.getUser(dropship.getIp());
            if (user != null) {
                boolean balanceModified;

                // subtract leave cost
                balanceModified = data.modifyBalance(user.getAccountId(), -1000);
                if (balanceModified) {
                    System.out.println("Balance modified successfully");
                } else {
                    System.out.println("Balance modification failed");
                }

                // add sold power-up total
                balanceModified = data.modifyBalance(user.getAccountId(), totalPowerupValue);
                if (balanceModified) {
                    System.out.println("Power-ups successfully");
                } else {
                    System.out.println("Failed to sell power-ups");
                }
            }
        }
    }

    public void create() {
        if (game != null) {
            return;
        }
        synchronized (this.monitor) {
            this.game = new Game();
            game.getGameBoard().setBoard(new GameBoardBuilder(FIELD_DIM,monitor).inMemoryGameRepositoryInitialize().build());
            startRepairTimer();
            startPowerUpSpawnTimer();
        }
    }

    private void startRepairTimer() {
        Timer repairTimer = new Timer();
        repairTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized (monitor) {
                    for (Dropship dropship : game.getDropships().values()) {
                        dropship.repairUnits();
                        int repairedLife = Math.min(dropship.getLife() + 1, 300);
                        dropship.setLife(repairedLife);
                    }
                }
            }
        }, 0, 1000);
    }

    // ------------ Spawn Methods ------------
    @Override
    public long spawnMiner(long dropshipId) throws TankDoesNotExistException, LimitExceededException {
        synchronized (this.monitor) {
            Dropship dropship = game.getDropship(dropshipId);
            if (dropship == null) {
                throw new TankDoesNotExistException(dropshipId);
            }
            if (dropship.getNumMiners() <= 0) {
                List<Long> miners = dropship.getMinerIds();
                return miners.get(0);
            }

            long minerId = this.idGenerator.getAndIncrement();
            Miner miner = new Miner(minerId, Direction.Up, dropship.getIp());

            // Find a free space near the dropship to spawn the miner
            FieldHolder spawningPoint = findFreeSpace(dropship.getParent());
            if (spawningPoint != null) {
                spawningPoint.setFieldEntity(miner);
                miner.setParent(spawningPoint);
                game.getMiners().put(minerId, miner);
                dropship.setNumMiners(dropship.getNumMiners() - 1);
                dropship.addMiner(minerId);
                return minerId;
            } else {
                return dropshipId;
            }
        }
    }

    @Override
    public long spawnTank(long dropshipId) throws TankDoesNotExistException, LimitExceededException {
        synchronized (this.monitor) {
            Dropship dropship = game.getDropship(dropshipId);
            if (dropship == null) {
                throw new TankDoesNotExistException(dropshipId);
            }

            if (dropship.getNumTanks() <= 0) {
                List<Long> tanks = dropship.getTankIds();
                return tanks.get(0);
            }

            long tankId = this.idGenerator.getAndIncrement();
            Tank tank = new Tank(tankId, Direction.Up, dropship.getIp());

            FieldHolder spawningPoint = findFreeSpace(dropship.getParent());
            if (spawningPoint != null) {
                spawningPoint.setFieldEntity(tank);
                tank.setParent(spawningPoint);
                game.getTanks().put(tankId, tank);
                dropship.setNumTanks(dropship.getNumTanks() - 1);
                dropship.addTank_(tankId);
                return tankId;
            } else {
                return dropshipId;
            }
        }
    }

    public FieldHolder findFreeSpace(FieldHolder startingPoint) {
        boolean infoLog = false;
        int startIndex = game.getGameBoard().getBoard().indexOf(startingPoint);
        int x = startIndex % FIELD_DIM;
        int y = startIndex / FIELD_DIM;
        int dx = 0;
        int dy = -1;
        int maxSteps = 2 * (FIELD_DIM - 1);

        LogUtil.log(log, infoLog, "Starting point: {}, Start index: {}, Start coordinates: ({}, {}), Max steps: {}",
                startingPoint, startIndex, x, y, maxSteps);

        for (int i = 0; i < maxSteps; i++) {
            LogUtil.log(log, infoLog, "Step: {}, Current coordinates: ({}, {})", i, x, y);

            if (0 <= x && x < FIELD_DIM && 0 <= y && y < FIELD_DIM) {
                FieldHolder currentField = game.getGameBoard().getBoard().get(y * FIELD_DIM + x);
                LogUtil.log(log, infoLog, "Current field: {}", currentField);

                if (!currentField.isPresent()) {
                    LogUtil.log(log, infoLog, "Free space found: {}", currentField);
                    return currentField;
                }
            } else {
                log.warn("Out of bounds: ({}, {})", x, y);
                int temp = dx;
                dx = -dy;
                dy = temp;
                LogUtil.log(log, infoLog, "Direction changed: dx={}, dy={}", dx, dy);
            }

            x += dx;
            y += dy;
        }

        LogUtil.log(log, infoLog, "No free space found");
        return null;
    }




    //------------- Power Up Spawning -----------------//

    private void startPowerUpSpawnTimer() {
        Timer powerUpSpawnTimer = new Timer();
        Random random = new Random();
        /**
         * The probability of showing a new item should be calculated
         * as 25% * P/(N + 1), where P is the number of Players currently in
         * the game and N is the number of items already on the board (N is
         * decremented whenever an item is picked up)
         *
         * Shane made it so that more are spawned. Also the equation is simpler now.
         */

        // probability out of 100 that a power up spawns.
        powerUpSpawnTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized (monitor) {
                    float numPowerUps = (float) game.getNumPowerups() + 1;
                    float numDropShips = (float) game.getDropships().size();
                    int probability = (int) (5 * numDropShips/numPowerUps);
                    int lottery = random.nextInt(100);
                    //System.out.println("Current Powerup Spawn Probability" + probability);
                    //System.out.println(game.getDropships().size());
                    if (lottery < probability) {
                        spawnPowerUp(); // Spawn A powerup
                    }
                }
            }
        }, 0, 100); // Attempt to spawn every second.
    }


    private void spawnPowerUp() {
        synchronized (this.monitor) {
            // Generate random coordinates for the power-up
            Random random = new Random();
            int x = Math.abs(random.nextInt(FIELD_DIM));
            int y = Math.abs(random.nextInt(FIELD_DIM));
            int lottery = Math.abs(random.nextInt(100));
            // get a random space.
            FieldHolder startingPoint = game.getHolderGrid().get(x * y);
            FieldHolder spawnLocation = findFreeSpace(startingPoint);
            // Create a power-up instance and add it to the game world
            PowerUpEntity powerUp;
            if (lottery >= 0 && lottery <= 25) {
//                System.out.println("Setting thingamajig power-up in spawn");
                powerUp = new ThingamajigEntity(spawnLocation.getPosition());
                spawnLocation.getTerrain().setPresentItem(1); // presentItemValue of 1 for thingamajig
            } else if (lottery >= 26 && lottery <= 50) {
//                System.out.println("Setting AntiGrav power-up in spawn");
                powerUp = new AntiGravPowerUpEntity(spawnLocation.getPosition());
                spawnLocation.getTerrain().setPresentItem(2); // 1 thing, 2 anti, 3 is fusion.
            } else { // it has to be a FusionReactor
//                System.out.println("Setting Fusion Reactor power-up in spawn");
                powerUp = new FusionReactorPowerUpEntity(spawnLocation.getPosition());
                spawnLocation.getTerrain().setPresentItem(3);
            }

//            System.out.println("Spawning power-up. Type: " + powerUp.getType() + " pos: " + powerUp.getPos());

            // increment current powerup counter.
            game.incrementnumPowerups();

            spawnLocation.clearField();
            spawnLocation.setFieldEntity(powerUp);
            powerUp.setParent(spawnLocation);
            EventBus.getDefault().post(new SpawnEvent(powerUp.getIntValue(), powerUp.getPos()));

        }
    }

    public void decrementPowerUpCount() {
        game.decrementnumPowerups();
    }
}
