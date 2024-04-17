package edu.unh.cs.cs619.bulletzone.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.commands.MineCommand;
import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.commands.FireCommand;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.LimitExceededException;
import edu.unh.cs.cs619.bulletzone.model.entities.Miner;
import edu.unh.cs.cs619.bulletzone.model.commands.MoveCommand;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.util.LogUtil;

@Component
public class InMemoryGameRepository implements GameRepository {

    private static final int FIELD_DIM = 16;
    private static final int BULLET_PERIOD = 200;
    private static final int BULLET_DAMAGE = 1;

    private final Timer timer = new Timer();
    private final AtomicLong idGenerator = new AtomicLong();
    private final Object monitor = new Object();
    private Game game = null;
    private final int[] bulletDamage = {15, 30, 50};
    private final int[] bulletDelay = {500, 1000, 1500};
    private final int[] trackActiveBullets = {0, 0};

    private static final Logger log = LoggerFactory.getLogger(InMemoryGameRepository.class);


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

            Long dropshipId = this.idGenerator.getAndIncrement();

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
                        PlayableEntity entity = game.getPlayableEntity(entityId);
                        if (entity == null) {
                            throw new TankDoesNotExistException(entityId);
                        }

                        FieldHolder currentField = entity.getParent();
                        int currentPosition = currentField.getPosition();
                        int currentX = currentPosition % FIELD_DIM;
                        int currentY = currentPosition / FIELD_DIM;

                        boolean stoppedX = false;
                        boolean stoppedY = false;
                        boolean stoppedXTwice = false;
                        boolean stoppedYTwice = false;

                        PlayableEntity playableEntity = game.getPlayableEntity(entityId);
                        int sleepTime = playableEntity.getAllowedMoveInterval() * 2;

                        while ((!playableEntity.isHasActionQueued() && ((currentX != targetX || currentY != targetY)))
                                && !(stoppedXTwice || stoppedYTwice)) {

                            stoppedXTwice = false;
                            stoppedYTwice = false;

                            // X movement
                            while (!playableEntity.isHasActionQueued() && currentX != targetX) {
                                Direction directionX = currentX < targetX ? Direction.Right : Direction.Left;
                                MoveCommand moveCommand = new MoveCommand(entityId, directionX);
                                boolean movedX = moveCommand.execute(playableEntity);
                                if (!movedX) {
                                    if (stoppedX) {
                                        stoppedXTwice = true;
                                    }
                                    stoppedX = true;
                                    System.out.println("Unable to move further in X direction. Stopping.");
                                    break;
                                }
                                currentField = entity.getParent();
                                currentPosition = currentField.getPosition();
                                currentX = currentPosition % FIELD_DIM;
                                currentY = currentPosition / FIELD_DIM;
                                System.out.println("Current Position: (" + currentX + ", " + currentY + ")");
                                System.out.println("Target Position: (" + targetX + ", " + targetY + ")");
                                Thread.sleep(sleepTime);

                                stoppedX = false;
                            }

                            // Y movement
                            while (!playableEntity.isHasActionQueued() && currentY != targetY) {
                                Direction directionY = currentY < targetY ? Direction.Down : Direction.Up;
                                MoveCommand moveCommand = new MoveCommand(entityId, directionY);
                                boolean movedY = moveCommand.execute(playableEntity);
                                if (!movedY) {
                                    if (stoppedY) {
                                        stoppedYTwice = true;
                                    }
                                    stoppedY = true;
                                    System.out.println("Unable to move further in Y direction. Stopping.");
                                    break;
                                }
                                currentField = entity.getParent();
                                currentPosition = currentField.getPosition();
                                currentX = currentPosition % FIELD_DIM;
                                currentY = currentPosition / FIELD_DIM;
                                System.out.println("Current Position: (" + currentX + ", " + currentY + ")");
                                System.out.println("Target Position: (" + targetX + ", " + targetY + ")");
                                Thread.sleep(sleepTime);

                                stoppedY = false;
                            }
                        }
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
    public void leave(long tankId) throws TankDoesNotExistException {
        synchronized (this.monitor) {
            if (!this.game.getTanks().containsKey(tankId)) {
                throw new TankDoesNotExistException(tankId);
            }

            System.out.println("leave() called, tank ID: " + tankId);

            Tank tank = game.getTanks().get(tankId);
            FieldHolder parent = tank.getParent();
            parent.clearField();
            game.removeTank(tankId);
        }
    }

    public void create() {
        if (game != null) {
            return;
        }
        synchronized (this.monitor) {
            this.game = new Game();
            game.getGameBoard().setBoard(new GameBoardBuilder(FIELD_DIM,monitor).inMemoryGameReposiryInitialize().build());
            startRepairTimer();
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



}
