package edu.unh.cs.cs619.bulletzone.ui;

public class GridModel {
    private GridCell[][] grid;
    private int[][] rawData;

    public void updateGrid(int[][] newData) {
        rawData = newData;
        grid = new GridCell[16][16];
        for (int i = 0; i < newData.length; i++) {
            for (int j = 0; j < newData[0].length; j++) {
                grid[i][j] = new GridCell(newData[i][j], i, j);
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
