package edu.unh.cs.cs619.bulletzone.model.commands;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EjectPowerUpCommandTest {
    static Game game = new Game();
    Tank tank;

    @Before
    public void setup() {
        // Build empty board for testing
        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16,new Object()).build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2);

        tank = new Tank(1, Direction.Up,"127.0.0.1");

        fieldElement.setFieldEntity(tank);
        tank.setParent(fieldElement);

        game.addTank(tank);

        tank.setDirection(Direction.Down);

//        fieldElement.getNeighbor(Direction.Down).getEntity().setParent(null);
        fieldElement.getNeighbor(Direction.Down).clearField();
    }

    @After
    public void tearDown() {
        game.removeTank(1);
    }
    @Test
    public void ejectCommand_EjectPowerPp_EjectToFront() {
        tank.pickupPowerUp(PowerUpType.AntiGrav);

        EjectPowerUpCommand ejectPowerUpCommand = new EjectPowerUpCommand(tank.getId());
        PlayableEntity playableEntity = game.getPlayableEntity(tank.getId());
        game.incrementnumPowerups();

        try {
            assertTrue(ejectPowerUpCommand.execute(playableEntity));
        } catch (TankDoesNotExistException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(tank.getParent().getNeighbor(Direction.Down).getEntity());
    }
}