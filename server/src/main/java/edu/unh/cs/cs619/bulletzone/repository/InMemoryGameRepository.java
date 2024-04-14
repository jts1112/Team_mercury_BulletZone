package edu.unh.cs.cs619.bulletzone.repository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicLong;

import edu.unh.cs.cs619.bulletzone.model.Direction;
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

@Component
public class InMemoryGameRepository implements GameRepository {

    /**
     * Field dimensions
     */
    private static final int FIELD_DIM = 16;

    /**
     * Bullet step time in milliseconds
     */
    private static final int BULLET_PERIOD = 200;

    /**
     * Bullet's impact effect [life]
     */
    private static final int BULLET_DAMAGE = 1;

    /**
     * Tank's default life [life]
     */
    private final Timer timer = new Timer();
    private final AtomicLong idGenerator = new AtomicLong();
    private final Object monitor = new Object();
    private Game game = null;
    private final int[] bulletDamage = {10, 30, 50};
    private final int[] bulletDelay = {500, 1000, 1500};
    private final int[] trackActiveBullets = {0, 0};

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

            if (playableEntity instanceof Dropship) {
                System.out.println("Dropship move attempt blocked");
                return false;
            }

            MoveCommand moveCommand = new MoveCommand(entityId, direction);
            boolean moveResult = moveCommand.execute(playableEntity);

            System.out.println("Move result: " + moveResult);

            return moveResult;
        }
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
//            createFieldHolderGrid(game); // TODO removed because added into gameboard bulder.
            //TODO added
//            game.getHolderGrid().addAll(new GameBoardBuilder(game.getHolderGrid()).inMemoryGameReposiryInitialize().build());// OLD before the createFeildHolderGrid
//            game.getHolderGrid().addAll(new GameBoardBuilder().createFieldHolderGrid(FIELD_DIM,monitor).inMemoryGameReposiryInitialize().build());// change back to this  before the createFeildHolderGrid
            game.getGameBoard().setBoard(new GameBoardBuilder(FIELD_DIM,monitor).inMemoryGameReposiryInitialize().build());
        }
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

    private FieldHolder findFreeSpace(FieldHolder startingPoint) {
        int startIndex = game.getGameBoard().getBoard().indexOf(startingPoint);
        int x = startIndex % FIELD_DIM;
        int y = startIndex / FIELD_DIM;
        int dx = 0;
        int dy = -1;
        int maxSteps = 2 * (FIELD_DIM - 1);

        for (int i = 0; i < maxSteps; i++) {
            if (0 <= x && x < FIELD_DIM && 0 <= y && y < FIELD_DIM) {
                FieldHolder currentField = game.getGameBoard().getBoard().get(y * FIELD_DIM + x);
                if (!currentField.isPresent()) {
                    return currentField;
                }
            }

            if (x == (startIndex % FIELD_DIM) + dx && y == (startIndex / FIELD_DIM) + dy) {
                int temp = dx;
                dx = -dy;
                dy = temp;
            }
            x += dx;
            y += dy;
        }

        return null;
    }

}
