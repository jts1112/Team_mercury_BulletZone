package edu.unh.cs.cs619.bulletzone.ui;

import edu.unh.cs.cs619.bulletzone.util.UnitIds;

public class GridModel {
    private GridCell[][] grid;
    private int[][] rawData;
    private GridCellImageMapper mapper;

    public GridModel(UnitIds ids) {
        mapper = new GridCellImageMapper(ids);
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

    public int[][] getRawGrid() {
        return rawData;
    }

    public GridCell[][] getGrid() {
        return grid;
    }
}
