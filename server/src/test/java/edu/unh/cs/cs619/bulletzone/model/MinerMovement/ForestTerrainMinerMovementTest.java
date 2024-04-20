package edu.unh.cs.cs619.bulletzone.model.MinerMovement;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.commands.MoveCommand;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.Miner;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
import edu.unh.cs.cs619.bulletzone.model.entities.Wall;

/**
 * Tests for the Miner for the Forest Biome. Miner should not be able to move through Forest Biome
 * and should keep the same position as previous position was.
 */
public class ForestTerrainMinerMovementTest {

    static Game game = new Game();
    static Miner miner = new Miner(1L, Direction.Up,"127.0.0.1");

//    static PlayableEntity miner = new Miner(1,Direction.Up,"127.0.0.1");

    @BeforeClass
    public static void init() {
        // Speeding up miner for movement validity tests
        miner.setAllowedMoveInterval(0);
    }

    @Before
    public void setup() {
        // Build empty board for testing
        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16,new Object()).setRowTerrain(0,3).
                setRowTerrain(1,3).
                setRowTerrain(2,3).
                setRowTerrain(3,3).
                setRowTerrain(4,3).
                setRowTerrain(5,3).
                setRowTerrain(6,3).
                setRowTerrain(7,3).
                setRowTerrain(8,3).
                setRowTerrain(9,3).
                setRowTerrain(10,3).
                setRowTerrain(11,3).
                setRowTerrain(12,3).
                setRowTerrain(13,3).
                setRowTerrain(14,3).
                setRowTerrain(15,3).setMeadowTerrain(2*16 + 2).
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

        MoveCommand moveCommand = new MoveCommand(1L, Direction.Right);

        try {
            assertTrue(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
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

        // get position before moveCommand is executed
        int minerPosition = miner.getPosition();

        MoveCommand moveCommand = new MoveCommand(1L, Direction.Right);

        try {
            assertFalse(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Right, game.getMiner(1L).getDirection());
        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveRight_FacingRight() {
        miner.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Right);

        try {
            assertTrue(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
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
            assertFalse(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Left, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    // Tests for moving Left
    @Test
    public void execute_FacingUpMoveLeft_FacingLeft() {
        miner.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
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
            assertFalse(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Right, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingDownMoveLeft_FacingLeft() {
        miner.setDirection(Direction.Down);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Left);

        try {
            assertTrue(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
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
            assertFalse(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Left, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    // Tests for moving Up
    @Test
    public void execute_FacingUpMoveUp_MoveUp() {
        miner.setDirection(Direction.Up);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertFalse(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Up, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(2* 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveUp_FacingUp() {
        miner.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
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
            assertFalse(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Down, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(2*16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveUp_FacingUp() {
        miner.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Up);

        try {
            assertTrue(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
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
            assertFalse(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Up, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingRightMoveDown_FacingDown() {
        miner.setDirection(Direction.Right);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
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
            assertFalse(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction
        assertEquals(Direction.Down, game.getMiner(1L).getDirection());

        // check that miner has moved properly
        assertEquals(game.getHolderGrid().get(2 * 16 + 2).getPosition(), miner.getParent().getPosition());
    }

    @Test
    public void execute_FacingLeftMoveDown_FacingDown() {
        miner.setDirection(Direction.Left);

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertTrue(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
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
            assertFalse(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        try {
            assertFalse(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        miner.setAllowedMoveInterval(0);
        miner.setLastMoveTime(System.currentTimeMillis());
    }

    // movement into unavailable square tests
    @Test
    public void execute_FacingWallMove_ReturnsFalse() {
        miner.setDirection(Direction.Down);

        // add wall below miner
        FieldHolder fieldElementBelow = game.getHolderGrid().get(3 * 16 + 2);
        fieldElementBelow.setFieldEntity(new Wall(1000, 3 * 16 + 2));

        MoveCommand moveCommand = new MoveCommand(1, Direction.Down);

        try {
            assertFalse(moveCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }


    }
}
