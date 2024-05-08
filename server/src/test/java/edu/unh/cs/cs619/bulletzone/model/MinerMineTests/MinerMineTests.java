package edu.unh.cs.cs619.bulletzone.model.MinerMineTests;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.unh.cs.cs619.bulletzone.datalayer.BulletZoneData;
import edu.unh.cs.cs619.bulletzone.datalayer.user.GameUser;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.EntityDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.commands.MineCommand;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.Miner;
import edu.unh.cs.cs619.bulletzone.model.events.CreditEvent;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class MinerMineTests {

    @Mock
    EventBus eventBus;

    BulletZoneData database = new BulletZoneData();

    static Game game = new Game();
    static Miner miner = new Miner(1, Direction.Up,"127.0.0.1");

    static Object monitor = new Object();

    static GameUser newUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        miner = new Miner(1, Direction.Up,"127.0.0.1");
    }

    @Test
    public void MeadowTerrainMineTest() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9).
                build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2);
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);


        // Set up initial conditions
        MineCommand mineCommand = new MineCommand(miner.getId(), monitor, eventBus);

        // Execute the command
        mineCommand.execute(miner);

        // verify the credit event was succesfully posted to eventbus.
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        verify(eventBus, atLeastOnce()).post(new CreditEvent(2));

    }

    @Test
    public void ForestTerrainMineTest() throws EntityDoesNotExistException {

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
                setRowTerrain(15,3).setForestTerrain(2*16 + 2).
                build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2);
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);


        // Set up initial conditions
        MineCommand mineCommand = new MineCommand(miner.getId(), monitor, eventBus);

        // Execute the command
        mineCommand.execute(miner);

        // verify the credit event was succesfully posted to eventbus.
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        verify(eventBus, atLeastOnce()).post(new CreditEvent(0));

    }


    @Test
    public void HillsTerrainMineTest() throws EntityDoesNotExistException {

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


        // Set up initial conditions
        MineCommand mineCommand = new MineCommand(miner.getId(), monitor, eventBus);

        // Execute the command
        mineCommand.execute(miner);

        // verify the credit event was succesfully posted to eventbus.
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        verify(eventBus, atLeastOnce()).post(new CreditEvent(50));

    }

    @Test
    public void RockyTerrainMineTest() throws EntityDoesNotExistException {

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
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);


        // Set up initial conditions
        MineCommand mineCommand = new MineCommand(miner.getId(), monitor, eventBus);

        // Execute the command
        mineCommand.execute(miner);

        // verify the credit event was succesfully posted to eventbus.
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        verify(eventBus, atLeastOnce()).post(new CreditEvent(10));
    }

    @Test
    // TODO needs to be implemented
    public void IronTerrainMineTest() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16,new Object()).setLayerRowTerrain(0,0,6).
                setLayerRowTerrain(0,1,6).
                setLayerRowTerrain(0,2,6).
                setLayerRowTerrain(0,3,6).
                setLayerRowTerrain(0,4,6).
                setLayerRowTerrain(0,5,6).
                setLayerRowTerrain(0,6,6).
                setLayerRowTerrain(0,7,6).
                setLayerRowTerrain(0,8,6).
                setLayerRowTerrain(0,9,6).
                setLayerRowTerrain(0,10,6).
                setLayerRowTerrain(0,11,6).
                setLayerRowTerrain(0,12,6).
                setLayerRowTerrain(0,13,6).
                setLayerRowTerrain(0,14,6).
                setLayerRowTerrain(0,15,6).
                build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2);
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);


        // Set up initial conditions
        MineCommand mineCommand = new MineCommand(miner.getId(), monitor, eventBus);

        // Execute the command
        mineCommand.execute(miner);

        // verify the credit event was succesfully posted to eventbus.
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        verify(eventBus, atLeastOnce()).post(new CreditEvent(100));
    }


    @Test
    // TODO needs to be implemented
    public void GemTerrainMineTest() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16,new Object()).setLayerRowTerrain(0,0,7).
                setLayerRowTerrain(0,1,7).
                setLayerRowTerrain(0,2,7).
                setLayerRowTerrain(0,3,7).
                setLayerRowTerrain(0,4,7).
                setLayerRowTerrain(0,5,7).
                setLayerRowTerrain(0,6,7).
                setLayerRowTerrain(0,7,7).
                setLayerRowTerrain(0,8,7).
                setLayerRowTerrain(0,9,7).
                setLayerRowTerrain(0,10,7).
                setLayerRowTerrain(0,11,7).
                setLayerRowTerrain(0,12,7).
                setLayerRowTerrain(0,13,7).
                setLayerRowTerrain(0,14,7).
                setLayerRowTerrain(0,15,7).
                build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2);
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);


        // Set up initial conditions
        MineCommand mineCommand = new MineCommand(miner.getId(), monitor, eventBus);

        // Execute the command
        mineCommand.execute(miner);

        // verify the credit event was successfully posted to eventbus.
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        verify(eventBus, atLeastOnce()).post(new CreditEvent(300));
    }


    @Test
    // TODO needs to be implemented
    public void UbontiumTerrainMineTest() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16,new Object()).setLayerRowTerrain(0,0,8).
                setLayerRowTerrain(0,1,8).
                setLayerRowTerrain(0,2,8).
                setLayerRowTerrain(0,3,8).
                setLayerRowTerrain(0,4,8).
                setLayerRowTerrain(0,5,8).
                setLayerRowTerrain(0,6,8).
                setLayerRowTerrain(0,7,8).
                setLayerRowTerrain(0,8,8).
                setLayerRowTerrain(0,9,8).
                setLayerRowTerrain(0,10,8).
                setLayerRowTerrain(0,11,8).
                setLayerRowTerrain(0,12,8).
                setLayerRowTerrain(0,13,8).
                setLayerRowTerrain(0,14,8).
                setLayerRowTerrain(0,15,8).
                build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2);
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);


        // Set up initial conditions
        MineCommand mineCommand = new MineCommand(miner.getId(), monitor, eventBus);

        // Execute the command
        mineCommand.execute(miner);

        // verify the credit event was succesfully posted to eventbus.
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        verify(eventBus, atLeastOnce()).post(new CreditEvent(1000));
    }
}
