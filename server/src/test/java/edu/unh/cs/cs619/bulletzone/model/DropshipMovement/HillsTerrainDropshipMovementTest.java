package edu.unh.cs.cs619.bulletzone.model.DropshipMovement;
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
import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.Wall;

/**
 * Tests for the Dropship for the Forest Biome. Dropship should not be able to move through Forest Biome
 * and should keep the same position as previous position was.
 */
public class HillsTerrainDropshipMovementTest {

    static Game game = new Game();
    static Dropship dropship = new Dropship(1L, Direction.Up,"127.0.0.1");

    @BeforeClass
    public static void init() {
        // Speeding up dropship for movement validity tests
        dropship.setAllowedMoveInterval(0);
    }

    @Before
    public void setup() {
        // Build empty board for testing
        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16,new Object()).setRowTerrain(0,2).
                setRowTerrain(1,2).
                setRowTerrain(2,2).
                setRowTerrain(3,2).
                setRowTerrain(4,2).
                setRowTerrain(5,2).
                setRowTerrain(6,2).
                setRowTerrain(7,2).
                setRowTerrain(8,2).
                setRowTerrain(9,2).
                setRowTerrain(10,2).
                setRowTerrain(11,2).
                setRowTerrain(12,2).
                setRowTerrain(13,2).
                setRowTerrain(14,2).
                setRowTerrain(15,2).setMeadowTerrain(2*16 + 2).
                build());



        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2);

        fieldElement.setFieldEntity(dropship);
        dropship.setParent(fieldElement);

        game.addDropship(dropship);
    }

    @After
    public void tearDown() {
        game.removeDropship(1);
    }

    // Movement validity tests

    // Tests for moving up
    @Test
    public void execute_FacingUpMoveRight_FacingRight() {
        dropship.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1L, Direction.Right);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Right, game.getDropship(1L).getDirection());

        // check that dropship hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }




    @Test
    public void execute_FacingRightMoveRight_MoveRight() {
        dropship.setDirection(Direction.Right);

        // get position before moveCommand is executed
        int dropshipPosition = dropship.getPosition();

        MoveCommand moveCommand = new MoveCommand(1L, Direction.Right);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Right, game.getDropship(1L).getDirection());
        // check that dropship has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveRight_FacingRight() {
        dropship.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Right);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Right, game.getDropship(1L).getDirection());

        // check that dropship hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveRight_MoveRight() {
        dropship.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Right);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Right, game.getDropship(1L).getDirection());

        // check that dropship has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    // Tests for moving Left
    @Test
    public void execute_FacingUpMoveLeft_FacingLeft() {
        dropship.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Left, game.getDropship(1L).getDirection());

        // check that dropship hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveLeft_MoveLeft() {
        dropship.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Left, game.getDropship(1L).getDirection());

        // check that dropship has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveLeft_FacingLeft() {
        dropship.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Left, game.getDropship(1L).getDirection());

        // check that dropship hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveLeft_MoveLeft() {
        dropship.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Left, game.getDropship(1L).getDirection());

        // check that dropship has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    // Tests for moving Up
    @Test
    public void execute_FacingUpMoveUp_MoveUp() {
        dropship.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Up, game.getDropship(1L).getDirection());

        // check that dropship has moved properly
        assertEquals(game.getHolderGrid().get(2* 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveUp_FacingUp() {
        dropship.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Up, game.getDropship(1L).getDirection());

        // check that dropship hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveUp_MoveUp() {
        dropship.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Up, game.getDropship(1L).getDirection());

        // check that dropship has moved properly
        assertEquals(game.getHolderGrid().get(2*16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveUp_FacingUp() {
        dropship.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Up, game.getDropship(1L).getDirection());

        // check that dropship hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    // Tests for moving Down
    @Test
    public void execute_FacingUpMoveDown_MoveDown() {
        dropship.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that droship is facing the correct direction
        assertEquals(Direction.Down, game.getDropship(1L).getDirection());

        // check that dropship has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveDown_FacingDown() {
        dropship.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Down, game.getDropship(1L).getDirection());

        // check that dropship hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveDown_MoveDown() {
        dropship.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Down, game.getDropship(1L).getDirection());

        // check that dropship has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveDown_FacingDown() {
        dropship.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that dropship is facing the correct direction
        assertEquals(Direction.Down, game.getDropship(1L).getDirection());

        // check that dropship hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), dropship.getParent().getPosition());
    }

    // Movement timing constraint test
    @Test
    public void execute_MoveQuickly_ReturnsFalse() {
        // Reset dropship moving interval to normal
        dropship.setAllowedMoveInterval(500);

        dropship.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        try {
            assertFalse(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        dropship.setAllowedMoveInterval(0);
        dropship.setLastMoveTime(System.currentTimeMillis());
    }

    // movement into unavailable square tests
    @Test
    public void execute_FacingWallMove_ReturnsFalse() {
        dropship.setDirection(Direction.Down);

        // add wall below dropship
        FieldHolder fieldElementBelow = game.getHolderGrid().get(3 * 16 + 2);
        fieldElementBelow.setFieldEntity(new Wall(1000, 3 * 16 + 2));

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(dropship));
        } catch(EntityDoesNotExistException e) {
            fail();
        }


    }
}
