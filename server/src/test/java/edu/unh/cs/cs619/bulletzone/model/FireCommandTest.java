package edu.unh.cs.cs619.bulletzone.model;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class FireCommandTest {
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

        game.addTank("127.0.0.1", tank);

        tank.setDirection(Direction.Down);
    }

    @After
    public void tearDown() {
        game.removeTank(1);
    }

    // firing timing when correct
    @Test
    public void execute_correctTiming_Fires() {
        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2);

        Tank tank = new Tank(1, Direction.Up,"127.0.0.1");

        fieldElement.setFieldEntity(tank);
        tank.setParent(fieldElement);

        game.addTank("127.0.0.1", tank);

        tank.setDirection(Direction.Down);

        try {
            assertTrue(new FireCommand(1L, 1).execute(game));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        tank.setLastFireTime(System.currentTimeMillis());

        try {
            assertTrue(new FireCommand(1L, 1).execute(game));
        } catch(TankDoesNotExistException e) {
            fail();
        }
    }

    // doesn't fire when too soon
    @Test
    public void execute_IncorrectTiming_FailsToFire() {
        try {
            assertTrue(new FireCommand(1L, 1).execute(game));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        try {
            assertFalse(new FireCommand(1L, 1).execute(game));
        } catch(TankDoesNotExistException e) {
            fail();
        }
    }

    // doesn't fire more than the max bullets
    @Test
    public void execute_MoreThanMaxBullets_FailsToFire() {

        for (int i = 0; i < tank.getAllowedNumberOfBullets(); i++) {
            try {
                assertTrue(new FireCommand(1L, 1).execute(game));
            } catch(TankDoesNotExistException e) {
                fail();
            }

            tank.setLastFireTime(System.currentTimeMillis());
        }

        try {
            assertFalse(new FireCommand(1L, 1).execute(game));
        } catch(TankDoesNotExistException e) {
            fail();
        }
    }
}