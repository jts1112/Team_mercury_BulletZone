package edu.unh.cs.cs619;

import edu.unh.cs.cs619.bulletzone.R;
import edu.unh.cs.cs619.bulletzone.ui.GridCellImageMapper;
import edu.unh.cs.cs619.bulletzone.util.UnitIds;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UnitHealthDisplayTest {

    @Before
    public void setup() {
        UnitIds ids = UnitIds.getInstance();
        ids.setIds(1L);
    }

    @Test
    public void testOtherTankFullHealth() {
        GridCellImageMapper mapper = GridCellImageMapper.getInstance();

        // Simulate a cell value representing another tank with full health
        int cellValue = 10001000; // Other tank, full health

        // Expected image resource for full health
        int expectedImageResource = R.drawable.tank_icon_enemy_full0;

        // Get the actual image resource
        int actualImageResource = mapper.getEntityImageResource(cellValue);

        // Assert that the actual image resource matches the expected one
        assertEquals(expectedImageResource, actualImageResource);
    }

    @Test
    public void testOtherTankHalfHealth() {
        GridCellImageMapper mapper = GridCellImageMapper.getInstance();

        // Simulate a cell value representing another tank with half health
        int cellValue = 10000500; // Other tank, half health

        // Expected image resource for half health
        int expectedImageResource = R.drawable.tank_icon_enemy_low0;

        // Get the actual image resource
        int actualImageResource = mapper.getEntityImageResource(cellValue);

        // Assert that the actual image resource matches the expected one
        assertEquals(expectedImageResource, actualImageResource);
    }

    @Test
    public void testOtherTankLowHealth() {
        GridCellImageMapper mapper = GridCellImageMapper.getInstance();

        // Simulate a cell value representing another tank with low health
        int cellValue = 10_000_090; // Other tank, low health

        // Expected image resource for low health
        int expectedImageResource = R.drawable.tank_icon_enemy_very_low0;

        // Get the actual image resource
        int actualImageResource = mapper.getEntityImageResource(cellValue);

        // Assert that the actual image resource matches the expected one
        assertEquals(expectedImageResource, actualImageResource);
    }

    @Test
    public void testOtherMinerFullHealth() {
        GridCellImageMapper mapper = GridCellImageMapper.getInstance();

        // Simulate a cell value representing another miner with full health
        int cellValue = 20001200; // Other miner, full health

        // Expected image resource for full health
        int expectedImageResource = R.drawable.miner_enemy_full0;

        // Get the actual image resource
        int actualImageResource = mapper.getEntityImageResource(cellValue);

        // Assert that the actual image resource matches the expected one
        assertEquals(expectedImageResource, actualImageResource);
    }

    @Test
    public void testOtherMinerHalfHealth() {
        GridCellImageMapper mapper = GridCellImageMapper.getInstance();

        // Simulate a cell value representing another miner with half health
        int cellValue = 20000600; // Other miner, half health

        // Expected image resource for half health
        int expectedImageResource = R.drawable.miner_enemy_low0;

        // Get the actual image resource
        int actualImageResource = mapper.getEntityImageResource(cellValue);

        // Assert that the actual image resource matches the expected one
        assertEquals(expectedImageResource, actualImageResource);
    }

    @Test
    public void testOtherMinerLowHealth() {
        GridCellImageMapper mapper = GridCellImageMapper.getInstance();

        // Simulate a cell value representing another miner with low health
        int cellValue = 20000090; // Other miner, low health

        // Expected image resource for low health
        int expectedImageResource = R.drawable.miner_enemy_very_low0;

        // Get the actual image resource
        int actualImageResource = mapper.getEntityImageResource(cellValue);

        // Assert that the actual image resource matches the expected one
        assertEquals(expectedImageResource, actualImageResource);
    }

    @Test
    public void testOtherDropshipFullHealth() {
        GridCellImageMapper mapper = GridCellImageMapper.getInstance();

        // Simulate a cell value representing another dropship with full health
        int cellValue = 30002800; // Other dropship, full health

        // Expected image resource for full health
        int expectedImageResource = R.drawable.dropship2full;

        // Get the actual image resource
        int actualImageResource = mapper.getEntityImageResource(cellValue);

        // Assert that the actual image resource matches the expected one
        assertEquals(expectedImageResource, actualImageResource);
    }

    @Test
    public void testOtherDropshipHalfHealth() {
        GridCellImageMapper mapper = GridCellImageMapper.getInstance();

        // Simulate a cell value representing another dropship with half health
        int cellValue = 30000900; // Other dropship, half health

        // Expected image resource for half health
        int expectedImageResource = R.drawable.dropship2low;

        // Get the actual image resource
        int actualImageResource = mapper.getEntityImageResource(cellValue);

        // Assert that the actual image resource matches the expected one
        assertEquals(expectedImageResource, actualImageResource);
    }

    @Test
    public void testOtherDropshipLowHealth() {
        GridCellImageMapper mapper = GridCellImageMapper.getInstance();

        // Simulate a cell value representing another dropship with low health
        int cellValue = 30000500; // Other dropship, low health

        // Expected image resource for low health
        int expectedImageResource = R.drawable.dropship2verylow;

        // Get the actual image resource
        int actualImageResource = mapper.getEntityImageResource(cellValue);

        // Assert that the actual image resource matches the expected one
        assertEquals(expectedImageResource, actualImageResource);
    }


}
