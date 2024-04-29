package edu.unh.cs.cs619.bulletzone.ui;

public class GridModel {
    private GridCell[][] grid;
    private int[][] rawData;
    private GridCell[][][] grid3d;
    private int[][][] rawData3d;
    private GridCellImageMapper mapper;

    public GridModel() {
        mapper = new GridCellImageMapper();
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

    public void updateGrid3d(int[][][] newData, int[][][] newTerrainData) {
        rawData3d = newData;
        grid3d = new GridCell[3][16][16];
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < newData.length; i++) {
                for (int j = 0; j < newData[0].length; j++) {
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
}
