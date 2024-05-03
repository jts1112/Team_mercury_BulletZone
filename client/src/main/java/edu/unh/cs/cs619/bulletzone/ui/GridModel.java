package edu.unh.cs.cs619.bulletzone.ui;

public class GridModel {
    private GridCell[][] grid;
    private int[][] rawData;
    private GridCellImageMapper mapper;

    private boolean hasFlag[][];

    public GridModel() {
        mapper = new GridCellImageMapper();
        this.hasFlag = new boolean[16][16];
        // Initialize all cells to false (no flag)
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                hasFlag[i][j] = false;
            }
        }
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

    public void setFlag(int row, int col) {
        // Set the flag at the specified position
        hasFlag[row][col] = true;
    }

    public void removeFlag(int row, int col) {
        // Remove the flag at the specified position
        hasFlag[row][col] = false;
    }

    public boolean hasFlag(int row, int col) {
        // Check if the specified position has a flag
        return hasFlag[row][col];
    }

    public int[][] getRawGrid() {
        return rawData;
    }

    public GridCell[][] getGrid() {
        return grid;
    }
}
