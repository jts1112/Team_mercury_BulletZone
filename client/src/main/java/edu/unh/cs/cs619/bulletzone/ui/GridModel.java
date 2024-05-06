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

//    public void updateGrid(int[][] newData, int[][] newTerrainData) {
//        rawData = newData;
//        grid = new GridCell[16][16];
//        for (int i = 0; i < newData.length; i++) {
//            for (int j = 0; j < newData[0].length; j++) {
//                int terrainResource = mapper.getTerrainImageResource(newTerrainData[i][j]);
//                int entityResource = mapper.getEntityImageResource(newData[i][j]);
//                GridCell cell = new GridCell(terrainResource, entityResource, i, j);
//                cell.setRotationForValue(newData[i][j]);
//                grid[i][j] = cell;
//            }
//        }
//    }

    public void initializeGrid3d(int[][][] newData, int[][][] newTerrainData) {
        int layers = 3;
        rawData3d = newData;
        grid3d = new GridCell[layers][newData[0].length][newData[0][0].length]; // 3 x 16 x 16 grid
        for (int k = 0; k < layers; k++) {
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

    public GridCell getCurrentUnit() {
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
                        return grid3d[k][i][j];
                    }
                }
            }
        }
        return null; // no cell found.
    }

    public Boolean checkCollision(){
        GridCell[][] currentGrid = getLayerGrid();
        GridCell currentUnit = getCurrentUnit();

        if (currentUnit == null) {
            return false; // No current unit, so no collision
        }

        int row = currentUnit.getRow();
        int col = currentUnit.getCol();
        int gridLength = currentGrid.length;

        // Check row for bullet
        for (int i = 0; i < gridLength; i++) {
            if (col >= 0 && col < gridLength && currentGrid[i][col].getEntityResourceID() == R.drawable.bullet1) {
                return true; // Bullet found in the same column as the unit
            }
        }

        // Check column for bullet
        for (int j = 0; j < gridLength; j++) {
            if (row >= 0 && row < gridLength && currentGrid[row][j].getEntityResourceID() == R.drawable.bullet1) {
                return true; // Bullet found in the same row as the unit
            }
        }

        // No nearby bullets
        return false;
    }



}
