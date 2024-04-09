package edu.unh.cs.cs619.bulletzone.ui;


import android.util.Log;

public class GridModel {

    private int[][] grid;

    public void updateGrid(int[][] newGrid) {
        // Log.d("model", "new grid ");
        this.grid = newGrid;
    }

    public int[][] getGrid() {
        return grid;
    }
}
