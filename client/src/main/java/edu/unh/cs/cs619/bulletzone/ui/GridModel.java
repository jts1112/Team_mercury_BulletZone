package edu.unh.cs.cs619.bulletzone.ui;

import edu.unh.cs.cs619.bulletzone.R;
import edu.unh.cs.cs619.bulletzone.util.UnitIds;

public class GridModel {
    private GridCell[][] grid;
    private int[][] rawData;
    private GridCell[][][] grid3d;
    private int[][][] rawData3d;
    private GridCellImageMapper mapper;
    private UnitIds ids;

    public GridModel() {
        mapper = GridCellImageMapper.getInstance();
        ids = UnitIds.getInstance();
    }

    public void updateGrid(int[][] newData, int[][] newTerrainData) {
        rawData = newData;
        grid = new GridCell[16][16];
        for (int i = 0; i < newData.length; i++) {
            for (int j = 0; j < newData[0].length; j++) {
                int terrainResource = mapper.getTerrainImageResource(newTerrainData[i][j]);
                int entityResource = mapper.getEntityImageResource(newData[i][j]);
                GridCell cell = new GridCell(terrainResource, entityResource, i, j);
                cell.setRotationForValue(newData[i][j]);
                grid[i][j] = cell;
            }
        }
    }

    public void initializeGrid3d(int[][][] newData, int[][][] newTerrainData) {
        rawData3d = newData;
        grid3d = new GridCell[3][16][16];
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < newData[0].length; i++) {
                for (int j = 0; j < newData[0][0].length; j++) {
                    int terrainResource = mapper.getTerrainImageResource(newTerrainData[k][i][j]);
                    int entityResource = mapper.getEntityImageResource(newData[k][i][j]);
                    GridCell cell = new GridCell(terrainResource, entityResource, i, j);
                    cell.setRotationForValue(newData[k][i][j]);
                    grid3d[k][i][j] = cell;
                }
            }
        }
    }

    public int[][] getRawGrid() {
        return rawData;
    }

    public GridCell[][] getGrid() {
        return grid;
    }

    public int[][][] getRawGrid3d() {
        return rawData3d;
    }

    public GridCell[][][] getGrid3d() {
        return grid3d;
    }

    public GridCell[][] getLayerGrid() {
        int controlledUnitId = (int) ids.getControlledUnitId();
        int val = 0;
        if (controlledUnitId == ids.getTankId()) {
            val = R.drawable.tank_icon2;
        } else if (controlledUnitId == ids.getMinerId()) {
            val = R.drawable.miner1;
        } else if (controlledUnitId == ids.getDropshipId()) {
            val = R.drawable.dropship1;
        }

        for (int k = 0; k < grid3d.length; k++) {
            for (int i = 0; i < grid3d[k].length; i++) {
                for (int j = 0; j < grid3d[k][i].length; j++) {
                    if (grid3d[k][i][j].getEntityResourceID() == val) {
                        return grid3d[k];
                    }
                }
            }
        }
        return grid3d[0]; // Controlled unit not found resorts to top layer
    }
}
