package edu.unh.cs.cs619.bulletzone.util;

/**
 * Created by simon on 10/1/14.
 */
public class GridWrapper {
    private int[][] grid;

    private int[][] terrainGrid;
    private long timeStamp;

    public GridWrapper(int[][] grid) {
        this.grid = grid;
        this.timeStamp = System.currentTimeMillis();
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public void setTerrainGrid(int[][] Terraingrid){
        this.terrainGrid = Terraingrid;
    }

    public int[][] getTerrainGrid(){
        return terrainGrid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
