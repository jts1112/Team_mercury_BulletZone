package edu.unh.cs.cs619.bulletzone.ui;
import android.util.Log;

import java.util.Arrays;

import edu.unh.cs.cs619.bulletzone.R;
import edu.unh.cs.cs619.bulletzone.util.UnitIds;

public class GridModel {
    private GridCell[][] grid;
    private int[][] rawData;
    private GridCell[][][] grid3d;
    private int[][][] rawData3d;
    private GridCellImageMapper mapper;
    private UnitIds ids;

    private boolean hasFlag[][];

    public GridModel() {
        mapper = GridCellImageMapper.getInstance();
        ids = UnitIds.getInstance();
        this.hasFlag = new boolean[16][16];
        // Initialize all cells to false (no flag)
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                hasFlag[i][j] = false;
            }
        }
    }

    public void initializeGrid3d(int[][][] newData, int[][][] newTerrainData) {
        int layers = 3;
        rawData3d = newData;
        grid3d = new GridCell[layers][newData[0].length][newData[0][0].length]; // 3 x 16 x 16 grid
        for (int k = 0; k < layers; k++) {
            for (int i = 0; i < newData[0].length; i++) {
                for (int j = 0; j < newData[0][0].length; j++) {
                    int terrainResource = mapper.getTerrainImageResource(newTerrainData[k][i][j]);
                    int entityResource = mapper.getEntityImageResource(newData[k][i][j]);
                    GridCell cell = new GridCell(terrainResource, entityResource, i, j, k);
                    cell.setRotationForValue(newData[k][i][j]);
                    grid3d[k][i][j] = cell;
                }
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

    public int[][][] getRawGrid3d() {
        return rawData3d;
    }

    public GridCell[][][] getGrid3d() {
        return grid3d;
    }

    public Integer[] getResourceValue(long controlledUnitId) {
        Integer[] resourceVal = {0};

        if (ids.getTankIdSet().contains(controlledUnitId)) {
            return ids.tankImageResources.get(controlledUnitId);
        } else if (ids.getMinerIdSet().contains(controlledUnitId)) {
            return ids.minerImageResources.get(controlledUnitId);
        } else {
            return resourceVal;
        }
    }

    public GridCell[][] getLayerGrid() {
        long controlledUnitId = ids.getControlledUnitId();
        int dropshipResource = -1;
        if (ids.getDropshipId() == controlledUnitId){
             dropshipResource = R.drawable.dropship1;
        }
        Integer[] resourceValueArray = getResourceValue(controlledUnitId);

        for (int k = 0; k < grid3d.length; k++) {
            for (int i = 0; i < grid3d[k].length; i++) {
                for (int j = 0; j < grid3d[k][i].length; j++) {
                    int entityResourceVal = grid3d[k][i][j].getEntityResourceID();
                    for (Integer resourceValue : resourceValueArray) {
                        if (entityResourceVal == resourceValue || entityResourceVal == dropshipResource) {
                            return grid3d[k];
                        }
                    }
                }
            }
        }
        return grid3d[0]; // Controlled unit not found resorts to top layer
    }

    public GridCell getCurrentUnit() {
        long controlledUnitId = ids.getControlledUnitId();
        int dropshipResource = -1;
        if (ids.getDropshipId() == controlledUnitId){
            dropshipResource = R.drawable.dropship1;
        }
        Integer[] resourceValueArray = getResourceValue(controlledUnitId);

        for (int k = 0; k < grid3d.length; k++) {
            for (int i = 0; i < grid3d[k].length; i++) {
                for (int j = 0; j < grid3d[k][i].length; j++) {
                    int entityResourceVal = grid3d[k][i][j].getEntityResourceID();
                    for (Integer resourceValue : resourceValueArray) {
                        if (entityResourceVal == resourceValue || entityResourceVal == dropshipResource) {
                            return grid3d[k][i][j];
                        }
                    }
                }
            }
        }
        return null; // no cell found.
    }

    public Boolean checkCollision(){
        GridCell[][] currentGrid = getLayerGrid();
        GridCell currentUnit = getCurrentUnit();

//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 16; i++) {
//            for (int j = 0; j < 16; j++) {
//                if (currentGrid[i][j].getEntityResourceID() == R.drawable.bullet1) {
////                    builder.append(currentGrid[i][j].toString() + " ");
//                    Log.d("INFO", "Bullet r:" + j + " c: " + i);
//                } else {
////                    builder.append("0");
//                }
//            }
////            builder.append("\n");
//        }
//
////        Log.d("Board", builder.toString());

        if (currentUnit == null) {
            Log.d("Collision","NUll");
            return false; // No current unit, so no collision
        }

        int row = currentUnit.getRow();
        int col = currentUnit.getCol();
//        Log.d("INFO", "Current r:" + row + " c: " + col);
        int gridLength = currentGrid.length;

        for (int i = 0; i < gridLength; i++) {
            if (col >= 0 && col < gridLength && currentGrid[i][col].getEntityResourceID() == R.drawable.bullet1) {
                return true; // Bullet found in the same column as the unit
            }
        }

        for (int j = 0; j < gridLength; j++) {
            if (row >= 0 && row < gridLength && currentGrid[row][j].getEntityResourceID() == R.drawable.bullet1) {
                return true; // Bullet found in the same row as the unit
            }
        }

        // No nearby bullets
        return false;
    }

    public GridCell[] getSurroundingCells() {
        GridCell[] surroundingCells = new GridCell[5];
        GridCell unit = getCurrentUnit();
        if (unit == null) {
            return null;
        }
        int row = unit.getRow();
        int col = unit.getCol();
        int prevRow = row - 1;
        int prevCol = col - 1;
        if (row == 0) {
            prevRow = 15;
        }
        if (col == 0) {
            prevCol = 15;
        }
        int layer = unit.getLayer();

        surroundingCells[0] = unit;
        surroundingCells[1] = grid3d[layer][(prevRow) % 16][col]; // up down left right
        surroundingCells[2] = grid3d[layer][(row + 1) % 16][col];
        surroundingCells[3] = grid3d[layer][row][(prevCol) % 16];
        surroundingCells[4] = grid3d[layer][row][(col + 1) % 16];

        return surroundingCells;
    }


}
