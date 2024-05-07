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
import edu.unh.cs.cs619.bulletzone.util.UnitIds;


public class BoardUpdateTest {
    private GridCell[][][] testBoard;
    GameData gameData;
    private final int BOARD_SIZE = 16;

    @Before
    public void setup() {
        testBoard = new GridCell[3][BOARD_SIZE][BOARD_SIZE];
        UnitIds ids = UnitIds.getInstance();
        ids.setIds(0, 1, 2);
        gameData = GameData.getInstance();
    }

    @Test
    public void RemovalEvent_RemoveUpdate_ReturnsEmpty() {
        int initialWallHealth = 500;
        int wallValue = 1000 + initialWallHealth;
        int row = 5;
        int col = 5;
        int position = row * 16 + col;
        testBoard[0][row][col] = new GridCell(0, wallValue, row, col);


        new RemovalEvent(5 * 16 + 5).applyTo(testBoard);

        Assert.assertEquals("Position should be empty after removal", 0, testBoard[0][row][col]);
    }

    @Test
    public void testDamageEvent() {

        int wallPosition = 7 * 16 + 7;
        int initialHealth = 500;
        testBoard[0][7][7] = new GridCell(0, 1000 + initialHealth, 7, 7);

        int damage = 200;
        int rawServerValue = 1000 + (initialHealth - damage);

        new DamageEvent(wallPosition, rawServerValue).applyTo(testBoard);

        Assert.assertEquals("Wall's health should be reduced by 200", rawServerValue, testBoard[0][7][7]);
    }

    @Test
    public void testSpawnEvent() {

        int tankPosition = 3 * 16 + 3;
        int tankValue = 10000000 + 2220072;


        new SpawnEvent(tankValue, tankPosition).applyTo(testBoard);

        Assert.assertEquals("Tank should be spawned at position", tankValue, testBoard[0][3][3]);
    }

    @Test
    public void testMoveEvent() {
        int oldPosition = 2 * 16 + 2;
        int newPosition = 3 * 16 + 3;
        int tankValue = 10000000 + 2220072;

        testBoard[0][2][2] = new GridCell(0, tankValue, 2, 2);


        new MoveEvent(tankValue, oldPosition, newPosition).applyTo(testBoard);

        Assert.assertEquals("Old position should be empty after move", 0, testBoard[0][2][2]);
        Assert.assertEquals("New position should contain tank after move", tankValue, testBoard[0][3][3]);
    }

    @Test
    public void CreditEvent_AddedCredits_ReturnsExpectedBalance() {
        CreditEvent creditEvent = new CreditEvent(500);

        creditEvent.applyTo(null);

        Assert.assertEquals("Player credits should be updated to 500", 500, gameData.getPlayerCredits());
    }

    @Test
    public void GameData_DamageUpdates_UpdatesEachUnit() {
        // Simulate damage to tank
        new DamageEvent(1, 10020600).applyTo(testBoard);

        // Verify tank life update
        Assert.assertEquals("Tank life should be updated to 60", 60, gameData.getTankLife());

        // Simulate damage to miner
        new DamageEvent(2, 20010800).applyTo(testBoard);

        // Verify miner life update
        Assert.assertEquals("Miner life should be updated to 80", 80, gameData.getMinerLife());

        // Simulate damage to dropship
        new DamageEvent(3, 30002500).applyTo(testBoard);

        // Verify dropship life update
        Assert.assertEquals("Dropship life should be updated to 250", 250, gameData.getDropshipLife());
    }

    @Test
    public void EntranceEvent_AddEntrance_ReturnsExpectedTerrainValue() {
        int entrancePosition = 5 * 16 + 5;
        int entranceValue = 6000;

        new EntranceEvent(entrancePosition, entranceValue).applyTo(testBoard);

        Assert.assertEquals("Entrance should be added at position", entranceValue, testBoard[0][5][5].getTerrainResourceID());
    }
}
