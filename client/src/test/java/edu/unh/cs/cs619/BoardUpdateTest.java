package edu.unh.cs.cs619;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.unh.cs.cs619.bulletzone.events.CreditEvent;
import edu.unh.cs.cs619.bulletzone.events.DamageEvent;
import edu.unh.cs.cs619.bulletzone.events.EntranceEvent;
import edu.unh.cs.cs619.bulletzone.events.GameData;
import edu.unh.cs.cs619.bulletzone.events.MoveEvent;
import edu.unh.cs.cs619.bulletzone.events.RemovalEvent;
import edu.unh.cs.cs619.bulletzone.events.SpawnEvent;
import edu.unh.cs.cs619.bulletzone.ui.GridCell;
import edu.unh.cs.cs619.bulletzone.ui.GridCellImageMapper;
import edu.unh.cs.cs619.bulletzone.util.UnitIds;


public class BoardUpdateTest {
    private GridCell[][][] testBoard;
    GameData gameData;
    private final int BOARD_SIZE = 16;
    GridCellImageMapper mapper;

    @Before
    public void setup() {
        testBoard = new GridCell[3][BOARD_SIZE][BOARD_SIZE];
        UnitIds ids = UnitIds.getInstance();
        ids.setIds(0);
        gameData = GameData.getInstance();
        mapper = GridCellImageMapper.getInstance();
    }

    @Test
    public void RemovalEvent_RemoveUpdate_ReturnsEmpty() {
        int initialWallHealth = 500;
        int wallValue = 1000 + initialWallHealth;
        int row = 5;
        int col = 5;
        int position = row * 16 + col;
        testBoard[0][row][col] = new GridCell(0, wallValue, row, col ,0);


        new RemovalEvent(5 * 16 + 5).applyTo(testBoard);

        Assert.assertEquals("Position should be empty after removal", 0, testBoard[0][row][col].getEntityResourceID());
    }

    @Test
    public void DamageEvent_DamageUpdate_ReturnsProperWallResource() {

        int wallPosition = 7 * 16 + 7;
        int initialHealth = 500;
        testBoard[0][7][7] = new GridCell(0, 1000 + initialHealth, 7, 7, 0);

        int damage = 200;
        int rawServerValue = 1000 + (initialHealth - damage);

        GridCell expected = new GridCell(0, mapper.getEntityImageResource(rawServerValue),
                7, 7, 0);
        new DamageEvent(wallPosition, rawServerValue).applyTo(testBoard);

        Assert.assertEquals("Wall's health should be reduced by 200",
                expected.getEntityResourceID(), testBoard[0][7][7].getEntityResourceID());
    }

//    @Test
//    public void GameData_DamageUpdates_UpdatesEachUnit() {
//        GridCellImageMapper mapper = GridCellImageMapper.getInstance();
//        UnitIds ids = UnitIds.getInstance();
//
//        int tankValue = 12220702;
//        int tankPosition = 3 * 16 + 3;
//        testBoard[0][3][3] = new GridCell(0, tankValue, 3, 3, 0);
//        Integer[] tankResources = {mapper.getEntityImageResource(12221002), mapper.getEntityImageResource(12220502), mapper.getEntityImageResource(12220022)};
//        ids.addTankId(222L, tankResources);
//
//        // Simulate damage to tank
//        new DamageEvent(tankPosition, 12220602).applyTo(testBoard);
//
//        // Verify tank life update
//        Assert.assertEquals("Tank life should be updated to 60", 60, gameData.getTankLife());
//
//        int minerValue = 20011000;
//        int minerPosition = 3 * 16 + 3;
//        testBoard[0][3][3] = new GridCell(0, minerValue, 3, 3, 0);
//        Integer[] minerResources = {mapper.getEntityImageResource(minerValue)};
//        ids.addMinerId(1L, minerResources);
//
//        // Simulate damage to miner
//        new DamageEvent(minerPosition, 20010800).applyTo(testBoard);
//
//        // Verify miner life update
//        Assert.assertEquals("Miner life should be updated to 80", 80, gameData.getMinerLife());
//
//        int dropshipValue = 30003000;
//        int dropshipPosition = 3 * 16 + 3;
//        testBoard[0][3][3] = new GridCell(0, dropshipValue, 3, 3, 0);
//
//        // Simulate damage to dropship
//        new DamageEvent(dropshipPosition, 30002500).applyTo(testBoard);
//
//        // Verify dropship life update
//        Assert.assertEquals("Dropship life should be updated to 250", 250, gameData.getDropshipLife());
//    }

    @Test
    public void testSpawnEvent() {

        int tankPosition = 3 * 16 + 3;
        int tankValue = 10000000 + 2220072;

        testBoard[0][3][3] = new GridCell(0, 0, 3, 3, 0);
        new SpawnEvent(tankValue, tankPosition).applyTo(testBoard);

        Assert.assertEquals("Tank should be spawned at position", mapper.getEntityImageResource(tankValue),
                testBoard[0][3][3].getEntityResourceID());
    }

    @Test
    public void testMoveEvent() {
        int oldPosition = 2 * 16 + 2;
        int newPosition = 3 * 16 + 3;
        int tankValue = mapper.getEntityImageResource(10000000 + 2220072);

        testBoard[0][2][2] = new GridCell(0, tankValue, 2, 2, 0);
        testBoard[0][3][3] = new GridCell(0, tankValue, 2, 2, 0);


        new MoveEvent(tankValue, oldPosition, newPosition).applyTo(testBoard);

        Assert.assertEquals("Old position should be empty after move",
                tankValue, testBoard[0][2][2].getEntityResourceID());
        Assert.assertEquals("New position should contain tank after move",
                0, testBoard[0][3][3].getEntityResourceID());
    }

    @Test
    public void CreditEvent_AddedCredits_ReturnsExpectedBalance() {
        CreditEvent creditEvent = new CreditEvent(500);

        creditEvent.applyTo(null);

        Assert.assertEquals("Player credits should be updated to 500", 500, gameData.getPlayerCredits());
    }

    @Test
    public void EntranceEvent_AddEntrance_ReturnsExpectedTerrainValue() {
        int entrancePosition = 5 * 16 + 5;
        int entranceValue = 6000;

        testBoard[0][5][5] = new GridCell(0, 0, 5, 5, 0);
        new EntranceEvent(entranceValue, entrancePosition).applyTo(testBoard);

        Assert.assertEquals("Entrance should be added at position",
                mapper.getTerrainImageResource(entranceValue), testBoard[0][5][5].getTerrainResourceID());
    }
}
