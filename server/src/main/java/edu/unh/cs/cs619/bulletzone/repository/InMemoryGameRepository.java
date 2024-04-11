package edu.unh.cs.cs619.bulletzone.repository;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicLong;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Dropship;
import edu.unh.cs.cs619.bulletzone.model.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.FireCommand;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.MoveCommand;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;

import static com.google.common.base.Preconditions.checkNotNull;

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
    private static final int TANK_LIFE = 100;
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

            game.addDropship(ip, dropship);

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
    public boolean move(long tankId, Direction direction)
            throws TankDoesNotExistException {
        synchronized (this.monitor) {
            // Find tank
            return new MoveCommand(tankId, direction).execute(game); ////
        }
    }

    @Override
    public boolean fire(long tankId, int bulletType)
            throws TankDoesNotExistException {
        synchronized (this.monitor) {
           return new FireCommand(tankId, bulletType).execute(game); ////
        }
    }

    @Override
    public void leave(long tankId)
            throws TankDoesNotExistException {
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
}
