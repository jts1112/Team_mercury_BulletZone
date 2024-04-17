package edu.unh.cs.cs619.bulletzone.util;

/**
 * Created by simon on 10/1/14.
 */
public class GridWrapper {
    private int[][] grid;

    private int[][] terrainGrid;

    private long timeStamp;

    public GridWrapper() {
    }

    public GridWrapper(int[][] grid) {
        this.grid = grid;
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public void setTerrainGrid(int[][] terrainGrid){
        this.terrainGrid = terrainGrid;
    }

    public int[][] getTerrainGrid(){
        return this.terrainGrid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
