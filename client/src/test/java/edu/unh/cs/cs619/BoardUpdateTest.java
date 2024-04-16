package edu.unh.cs.cs619;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.unh.cs.cs619.bulletzone.events.CreditEvent;
import edu.unh.cs.cs619.bulletzone.events.DamageEvent;
import edu.unh.cs.cs619.bulletzone.events.GameData;
import edu.unh.cs.cs619.bulletzone.events.MoveEvent;
import edu.unh.cs.cs619.bulletzone.events.RemovalEvent;
import edu.unh.cs.cs619.bulletzone.events.SpawnEvent;
import edu.unh.cs.cs619.bulletzone.util.UnitIds;


public class BoardUpdateTest {
    private int[][] testBoard;
    GameData gameData;
    private final int BOARD_SIZE = 16;

    @Before
    public void setup() {
        testBoard = new int[BOARD_SIZE][BOARD_SIZE];
        UnitIds ids = UnitIds.getInstance();
        ids.setIds(0, 1, 2);
        gameData = new GameData(ids);
    }

    @Test
    public void RemovalEvent_RemoveUpdate_ReturnsEmpty() {
        int initialWallHealth = 500;
        int wallValue = 1000 + initialWallHealth;
        int row = 5;
        int col = 5;
        int position = row * 16 + col;
        testBoard[row][col] = wallValue;


        new RemovalEvent(5 * 16 + 5).applyTo(testBoard, gameData);

        Assert.assertEquals("Position should be empty after removal", 0, testBoard[row][col]);
    }

    @Test
    public void testDamageEvent() {

        int wallPosition = 7 * 16 + 7;
        int initialHealth = 500;
        testBoard[7][7] = 1000 + initialHealth;

        int damage = 200;
        int rawServerValue = 1000 + (initialHealth - damage);

        new DamageEvent(wallPosition, rawServerValue).applyTo(testBoard, gameData);

        Assert.assertEquals("Wall's health should be reduced by 200", rawServerValue, testBoard[7][7]);
    }

    @Test
    public void testSpawnEvent() {

        int tankPosition = 3 * 16 + 3;
        int tankValue = 10000000 + 2220072;


        new SpawnEvent(tankValue, tankPosition).applyTo(testBoard, gameData);

        Assert.assertEquals("Tank should be spawned at position", tankValue, testBoard[3][3]);
    }

    @Test
    public void testMoveEvent() {
        int oldPosition = 2 * 16 + 2;
        int newPosition = 3 * 16 + 3;
        int tankValue = 10000000 + 2220072;

        testBoard[2][2] = tankValue;


        new MoveEvent(tankValue, oldPosition, newPosition).applyTo(testBoard, gameData);

        Assert.assertEquals("Old position should be empty after move", 0, testBoard[2][2]);
        Assert.assertEquals("New position should contain tank after move", tankValue, testBoard[3][3]);
    }

    @Test
    public void CreditEvent_AddedCredits_ReturnsExpectedBalance() {
        CreditEvent creditEvent = new CreditEvent(500, 100);

        creditEvent.applyTo(null, gameData);

        Assert.assertEquals("Player credits should be updated to 500", 500, gameData.getPlayerCredits());
    }

    @Test
    public void GameData_DamageUpdates_UpdatesEachUnit() {
        // Simulate damage to tank
        new DamageEvent(1, 10020600).applyTo(testBoard, gameData);

        // Verify tank life update
        Assert.assertEquals("Tank life should be updated to 60", 60, gameData.getTankLife());

        // Simulate damage to miner
        new DamageEvent(2, 20010800).applyTo(testBoard, gameData);

        // Verify miner life update
        Assert.assertEquals("Miner life should be updated to 80", 80, gameData.getMinerLife());

        // Simulate damage to dropship
        new DamageEvent(3, 30002500).applyTo(testBoard, gameData);

        // Verify dropship life update
        Assert.assertEquals("Dropship life should be updated to 250", 250, gameData.getDropshipLife());
    }
}
