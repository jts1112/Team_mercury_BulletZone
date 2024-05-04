package edu.unh.cs.cs619.bulletzone.model.MinerMineTests;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.unh.cs.cs619.bulletzone.datalayer.BulletZoneData;
import edu.unh.cs.cs619.bulletzone.datalayer.account.BankAccount;
import edu.unh.cs.cs619.bulletzone.datalayer.user.GameUser;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.commands.MineCommand;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.Miner;
import edu.unh.cs.cs619.bulletzone.model.events.CreditEvent;

import static org.hamcrest.core.IsInstanceOf.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    }

    @Test
    public void MeadowTerrainMineTest() throws TankDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setRowTerrain(0, 4).
                setRowTerrain(1, 4).
                setRowTerrain(2, 4).
                setRowTerrain(3, 4).
                setRowTerrain(4, 4).
                setRowTerrain(5, 4).
                setRowTerrain(6, 4).
                setRowTerrain(7, 4).
                setRowTerrain(8, 4).
                setRowTerrain(9, 4).
                setRowTerrain(10, 4).
                setRowTerrain(11, 4).
                setRowTerrain(12, 4).
                setRowTerrain(13, 4).
                setRowTerrain(14, 4).
                setRowTerrain(15, 4).
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
        verify(eventBus).post(new CreditEvent(2));

    }

    @Test
    public void ForestTerrainMineTest() throws TankDoesNotExistException {

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
        verify(eventBus).post(new CreditEvent(0));

    }


    @Test
    public void HillsTerrainMineTest() throws TankDoesNotExistException {

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
        verify(eventBus).post(new CreditEvent(50));

    }

    @Test
    public void RockyTerrainMineTest() throws TankDoesNotExistException {

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
        verify(eventBus).post(new CreditEvent(10));
    }

    @Test
    // TODO needs to be implemented
    public void IronTerrainMineTest() throws TankDoesNotExistException {

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
        verify(eventBus).post(new CreditEvent(10));
    }


    @Test
    // TODO needs to be implemented
    public void GemTerrainMineTest() throws TankDoesNotExistException {

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
        verify(eventBus).post(new CreditEvent(10));
    }


    @Test
    // TODO needs to be implemented
    public void UbontiumTerrainMineTest() throws TankDoesNotExistException {

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
        verify(eventBus).post(new CreditEvent(10));
    }


}
