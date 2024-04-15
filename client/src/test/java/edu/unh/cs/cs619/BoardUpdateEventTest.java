package edu.unh.cs.cs619;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.unh.cs.cs619.bulletzone.events.DamageEvent;
import edu.unh.cs.cs619.bulletzone.events.MoveEvent;
import edu.unh.cs.cs619.bulletzone.events.RemovalEvent;
import edu.unh.cs.cs619.bulletzone.events.SpawnEvent;


public class BoardUpdateEventTest {
    private int[][] testBoard;
    private final int BOARD_SIZE = 16;

    @Before
    public void setup() {
        testBoard = new int[BOARD_SIZE][BOARD_SIZE];
    }

    @Test
    public void testRemovalEvent() {
        int initialWallHealth = 500;
        int wallValue = 1000 + initialWallHealth;
        int row = 5;
        int col = 5;
        int position = row * 16 + col;
        testBoard[row][col] = wallValue;

        RemovalEvent removalEvent = new RemovalEvent(position);
        removalEvent.applyTo(testBoard, null);

        Assert.assertEquals("Position should be empty after removal", 0, testBoard[row][col]);
    }

    @Test
    public void testDamageEvent() {

        int wallPosition = 7 * 16 + 7;
        int initialHealth = 500;
        testBoard[7][7] = 1000 + initialHealth;

        int damage = 200;
        int rawServerValue = 1000 + (initialHealth - damage);
        new DamageEvent(wallPosition, rawServerValue).applyTo(testBoard, null);

        Assert.assertEquals("Wall's health should be reduced by 200", rawServerValue, testBoard[7][7]);
    }

    @Test
    public void testSpawnEvent() {

        int tankPosition = 3 * 16 + 3;
        int tankValue = 10000000 + 2220072;

        new SpawnEvent(tankValue, tankPosition).applyTo(testBoard, null);

        Assert.assertEquals("Tank should be spawned at position", tankValue, testBoard[3][3]);
    }

    @Test
    public void testMoveEvent() {
        int oldPosition = 2 * 16 + 2;
        int newPosition = 3 * 16 + 3;
        int tankValue = 10000000 + 2220072;

        testBoard[2][2] = tankValue;

        new MoveEvent(tankValue, oldPosition, newPosition).applyTo(testBoard, null);

        Assert.assertEquals("Old position should be empty after move", 0, testBoard[2][2]);
        Assert.assertEquals("New position should contain tank after move", tankValue, testBoard[3][3]);
    }

}
