package edu.unh.cs.cs619.bulletzone.model.TankMovement;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.EntityDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.commands.MoveCommand;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
import edu.unh.cs.cs619.bulletzone.model.entities.Wall;

public class RockyTerrainTankMovementTest {

    static Game game = new Game();
    static Tank tank = new Tank(1, Direction.Up,"127.0.0.1");

    @BeforeClass
    public static void init() {
        // Speeding up tank for movement validity tests
        tank.setAllowedMoveInterval(0);
    }

    @Before
    public void setup() {
        // Build empty board for testing
        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16,new Object()).setRowTerrain(0,1).
                setRowTerrain(1,1).
                setRowTerrain(2,1).
                setRowTerrain(3,1).
                setRowTerrain(4,1).
                setRowTerrain(5,1).
                setRowTerrain(6,1).
                setRowTerrain(7,1).
                setRowTerrain(8,1).
                setRowTerrain(9,1).
                setRowTerrain(10,1).
                setRowTerrain(11,1).
                setRowTerrain(12,1).
                setRowTerrain(13,1).
                setRowTerrain(14,1).
                setRowTerrain(15,1).
                build());



        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2);

        fieldElement.setFieldEntity(tank);
        tank.setParent(fieldElement);

        game.addTank(tank);
    }

    @After
    public void tearDown() {
        game.removeTank(1);
    }

    // Movement validity tests

    // Tests for moving up
    @Test
    public void execute_FacingUpMoveRight_FacingRight() {
        tank.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Right);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Right, game.getTank(1L).getDirection());

        // check that tank hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), tank.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveRight_MoveRight() {
        tank.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Right);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Right, game.getTank(1L).getDirection());

        // check that tank has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 3).getPosition(), tank.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveRight_FacingRight() {
        tank.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Right);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Right, game.getTank(1L).getDirection());

        // check that tank hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), tank.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveRight_MoveRight() {
        tank.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Right);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Left, game.getTank(1L).getDirection());

        // check that tank has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 3).getPosition(), tank.getParent().getPosition());
    }

    // Tests for moving Left
    @Test
    public void execute_FacingUpMoveLeft_FacingLeft() {
        tank.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Left, game.getTank(1L).getDirection());

        // check that tank hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), tank.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveLeft_MoveLeft() {
        tank.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Right, game.getTank(1L).getDirection());

        // check that tank has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 1).getPosition(), tank.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveLeft_FacingLeft() {
        tank.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Left, game.getTank(1L).getDirection());

        // check that tank hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), tank.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveLeft_MoveLeft() {
        tank.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Left, game.getTank(1L).getDirection());

        // check that tank has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 1).getPosition(), tank.getParent().getPosition());
    }

    // Tests for moving Up
    @Test
    public void execute_FacingUpMoveUp_MoveUp() {
        tank.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Up, game.getTank(1L).getDirection());

        // check that tank has moved properly
        assertEquals(game.getHolderGrid().get(16 + 2).getPosition(), tank.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveUp_FacingUp() {
        tank.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Up, game.getTank(1L).getDirection());

        // check that tank hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), tank.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveUp_MoveUp() {
        tank.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Down, game.getTank(1L).getDirection());

        // check that tank has moved properly
        assertEquals(game.getHolderGrid().get(16 + 2).getPosition(), tank.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveUp_FacingUp() {
        tank.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Up, game.getTank(1L).getDirection());

        // check that tank hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), tank.getParent().getPosition());
    }

    // Tests for moving Down
    @Test
    public void execute_FacingUpMoveDown_MoveDown() {
        tank.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Up, game.getTank(1L).getDirection());

        // check that tank has moved properly
        assertEquals(game.getHolderGrid().get(3 * 16 + 2).getPosition(), tank.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveDown_FacingDown() {
        tank.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Down, game.getTank(1L).getDirection());

        // check that tank hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), tank.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveDown_MoveDown() {
        tank.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Down, game.getTank(1L).getDirection());

        // check that tank has moved properly
        assertEquals(game.getHolderGrid().get(3 * 16 + 2).getPosition(), tank.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveDown_FacingDown() {
        tank.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that tank is facing the correct direction
        assertEquals(Direction.Down, game.getTank(1L).getDirection());

        // check that tank hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), tank.getParent().getPosition());
    }

    // Movement timing constraint test
    @Test
    public void execute_MoveQuickly_ReturnsFalse() {
        // Reset tank moving interval to normal
        tank.setAllowedMoveInterval(500);

        tank.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        try {
            assertFalse(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        tank.setAllowedMoveInterval(0);
        tank.setLastMoveTime(System.currentTimeMillis());
    }

    // movement into unavailable square tests
    @Test
    public void execute_FacingWallMove_ReturnsFalse() {
        tank.setDirection(Direction.Down);

        // add wall below tank
        FieldHolder fieldElementBelow = game.getHolderGrid().get(3 * 16 + 2);
        fieldElementBelow.setFieldEntity(new Wall(1000, 3 * 16 + 2));

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertFalse(moveCommand.execute(tank));
        } catch(EntityDoesNotExistException e) {
            fail();
        }


    }
}
