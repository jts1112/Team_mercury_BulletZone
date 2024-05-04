package edu.unh.cs.cs619.bulletzone.util;

/**
 * Created by simon on 10/1/14.
 */
public class GridWrapper {
    private int[][] grid;

    private int[][] terrainGrid;
    private int[][][] grid3d;

    private int[][][] terrainGrid3d;
    private long timeStamp;

    public GridWrapper() {}

    public GridWrapper(int[][] grid) {
        this.grid = grid;
        this.timeStamp = System.currentTimeMillis();
    }

    public GridWrapper(int[][][] grid) {
        this.grid3d = grid;
        this.timeStamp = System.currentTimeMillis();
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int[][][] getGrid3d() {
        return this.grid3d;
    }

    public void setGrid3d(int[][][] grid) {
        this.grid3d = grid;
    }

    public void setTerrainGrid(int[][] Terraingrid){
        this.terrainGrid = Terraingrid;
    }

    public int[][] getTerrainGrid(){
        return terrainGrid;
    }

    public void setTerrainGrid3d(int[][][] Terraingrid){
        this.terrainGrid3d = Terraingrid;
    }

    public int[][][] getTerrainGrid3d(){
        return terrainGrid3d;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
