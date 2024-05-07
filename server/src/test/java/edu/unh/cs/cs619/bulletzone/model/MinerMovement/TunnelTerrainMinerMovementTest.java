package edu.unh.cs.cs619.bulletzone.model.MinerMovement;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.unh.cs.cs619.bulletzone.datalayer.terrain.EntranceTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.HillsTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.MeadowTerrain;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.commands.DigCommand;
import edu.unh.cs.cs619.bulletzone.model.commands.MoveCommand;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.Miner;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
import edu.unh.cs.cs619.bulletzone.model.entities.Wall;

/**
 * Running Tests On Miner for the Meadow terrain. Miner should be able to move through Meadow terrain
 * no matter the orientation etc.
 */
public class TunnelTerrainMinerMovementTest {

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
        game.getHolderGrid().addAll(new GameBoardBuilder(16,new Object()).setLayerRowTerrain(0,0,2).
                setLayerRowTerrain(0,1,2).
                setLayerRowTerrain(0,2,2).
                setLayerRowTerrain(0,3,2).
                setLayerRowTerrain(0,4,2).
                setLayerRowTerrain(0,5,2).
                setLayerRowTerrain(0,6,2).
                setLayerRowTerrain(0,7,2).
                setLayerRowTerrain(0,8,2).
                setLayerRowTerrain(0,9,2).
                setLayerRowTerrain(0,10,2).
                setLayerRowTerrain(0,11,2).
                setLayerRowTerrain(0,12,2).
                setLayerRowTerrain(0,13,2).
                setLayerRowTerrain(0,14,2).
                setLayerRowTerrain(0,15,2).setEntranceTerrain(2*16+2,Direction.Down) // last one for top grid
                .setLayerRowTerrain(1,0,9).
                setLayerRowTerrain(1,1,9).
                setLayerRowTerrain(1,2,9).
                setLayerRowTerrain(1,3,9).
                setLayerRowTerrain(1,4,9).
                setLayerRowTerrain(1,5,9).
                setLayerRowTerrain(1,6,9).
                setLayerRowTerrain(1,7,9).
                setLayerRowTerrain(1,8,9).
                setLayerRowTerrain(1,9,9).
                setLayerRowTerrain(1,10,9).
                setLayerRowTerrain(1,11,9).
                setLayerRowTerrain(1,12,9).
                setLayerRowTerrain(1,13,9).
                setLayerRowTerrain(1,14,9).
                setLayerRowTerrain(1,15,9).setEntranceTerrain(2*16 + 2).
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


    @Test
    public void Miner_TunnelFromLowLayertoHighLayer() {
        miner.setDirection(Direction.Up);

        DigCommand digCommand = new DigCommand(1,new Object());
        try {
            assertTrue(digCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        MoveCommand moveDownCommand = new MoveCommand(1,Direction.Down);
        MoveCommand moveUpCommand = new MoveCommand(1,Direction.Up);
        MoveCommand moveAboveCommand = new MoveCommand(1,Direction.Above);
        try {
            assertTrue(moveDownCommand.execute(miner));
            assertTrue(moveDownCommand.execute(miner));
            assertTrue(moveUpCommand.execute(miner));
            assertTrue(moveUpCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction. since Moving dow  shouldnt change the non  leveling direction
        assertEquals(Direction.Up, game.getMiner(1L).getDirection());

        // asser that the surrounding terrain is the correct one for that layer. verifying tunnel success
        assertInstanceOf(MeadowTerrain.class,miner.getParent().getNeighbor(Direction.Down).getTerrain());
    }

    @Test
    public void Miner_TunnelFromHighLayertoLowLayer() {
        miner.setDirection(Direction.Up);

        DigCommand digCommand = new DigCommand(1,new Object());
        try {
            assertTrue(digCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        MoveCommand moveDownCommand = new MoveCommand(1,Direction.Down);
        MoveCommand moveUpCommand = new MoveCommand(1,Direction.Up);
        MoveCommand moveAboveCommand = new MoveCommand(1,Direction.Above);
        try {
            assertTrue(moveDownCommand.execute(miner));
            assertTrue(moveDownCommand.execute(miner));
            assertTrue(moveAboveCommand.execute(miner));
            assertTrue(moveUpCommand.execute(miner));
            assertTrue(moveUpCommand.execute(miner));
        } catch(TankDoesNotExistException e) {
            fail();
        }

        // check that miner is facing the correct direction. since Moving dow  shouldnt change the non  leveling direction
        assertEquals(Direction.Up, game.getMiner(1L).getDirection());

        // asser that the surrounding terrain is the correct one for that layer. verifying tunnel success
        assertInstanceOf(HillsTerrain.class,miner.getParent().getNeighbor(Direction.Down).getTerrain());
    }


}
