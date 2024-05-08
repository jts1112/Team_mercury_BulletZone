package edu.unh.cs.cs619.bulletzone.model.MinerMovement;
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
import edu.unh.cs.cs619.bulletzone.model.entities.Miner;
import edu.unh.cs.cs619.bulletzone.model.entities.Wall;

/**
 * Running Tests On Miner for the Hills terrain. Miner should be able to move through hills terrain
 * no matter the orientation etc.
 */
public class HillsTerrainMinerMovementTest {

    static Game game = new Game();
    static Miner miner = new Miner(1, Direction.Up,"127.0.0.1");

    @BeforeClass
    public static void init() {
        // Speeding up miner for movement validity tests
        miner.setAllowedMoveInterval(0);
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
                setRowTerrain(15,2).
                build());



        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2);

        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);
    }

    @After
    public void tearDown() {
        game.removeMiner(1);
    }

    // Movement validity tests

    // Tests for moving up
    @Test
    public void execute_FacingUpMoveRight_FacingRight() {
        miner.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Right);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Right, game.getMiner(1L).getDirection());

        // check that miner hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveRight_MoveRight() {
        miner.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Right);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Right, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 3).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveRight_FacingRight() {
        miner.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Right);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Right, game.getMiner(1L).getDirection());

        // check that miner hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveRight_MoveRight() {
        miner.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Right);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Left, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 3).getPosition(), miner.getParent().getPosition());
    }

    // Tests for moving Left
    @Test
    public void execute_FacingUpMoveLeft_FacingLeft() {
        miner.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Left, game.getMiner(1L).getDirection());

        // check that miner hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveLeft_MoveLeft() {
        miner.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Right, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 1).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveLeft_FacingLeft() {
        miner.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Left, game.getMiner(1L).getDirection());

        // check that miner hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveLeft_MoveLeft() {
        miner.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Left, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 1).getPosition(), miner.getParent().getPosition());
    }

    // Tests for moving Up
    @Test
    public void execute_FacingUpMoveUp_MoveUp() {
        miner.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Up, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveUp_FacingUp() {
        miner.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Up, game.getMiner(1L).getDirection());

        // check that miner hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveUp_MoveUp() {
        miner.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Down, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveUp_FacingUp() {
        miner.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Up, game.getMiner(1L).getDirection());

        // check that miner hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    // Tests for moving Down
    @Test
    public void execute_FacingUpMoveDown_MoveDown() {
        miner.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Up, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(3 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveDown_FacingDown() {
        miner.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Down, game.getMiner(1L).getDirection());

        // check that miner hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveDown_MoveDown() {
        miner.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Down, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(3 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveDown_FacingDown() {
        miner.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Down, game.getMiner(1L).getDirection());

        // check that miner hasn't moved if it isn't supposed to
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    // Movement timing constraint test
    @Test
    public void execute_MoveQuickly_ReturnsFalse() {
        // Reset miner moving interval to normal
        miner.setAllowedMoveInterval(500);

        miner.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        try {
            assertFalse(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }

        miner.setAllowedMoveInterval(0);
        miner.setLastMoveTime(System.currentTimeMillis());
    }

    // movement into unavailable square tests
    @Test
    public void execute_FacingWallMove_ReturnsFalse() {
        miner.setDirection(Direction.Down);

        // add wall below tank
        FieldHolder fieldElementBelow = game.getHolderGrid().get(3 * 16 + 2);
        Wall wall = new Wall(1000, 3 * 16 + 2);
        fieldElementBelow.setFieldEntity(wall);
        wall.setParent(fieldElementBelow);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertFalse(moveCommand.execute2(miner, game));
        } catch(EntityDoesNotExistException e) {
            fail();
        }


    }
}
