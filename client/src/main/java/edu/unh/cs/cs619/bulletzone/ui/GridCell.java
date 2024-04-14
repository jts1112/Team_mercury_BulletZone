package edu.unh.cs.cs619.bulletzone.ui;

import edu.unh.cs.cs619.bulletzone.R;

public class GridCell {
    private int resourceID;
    private int rawValue;
    private int row;
    private int col;

    private int terrainValue;

    public GridCell(int rawValue, int row, int col) {
        this.rawValue = rawValue;
        this.row = row;
        this.col = col;
        this.resourceID = getResourceIDForValue(rawValue);
    }

    public void setTerrainValue(int terrainValue) {
        this.terrainValue = terrainValue;
        if (rawValue == 0) {
            resourceID = getResourceIDForValue(rawValue);
        }
    }

    public int getTerrainValue() {
        return terrainValue;
    }

    public int getResourceID() {
        return resourceID;
    }

    public int getRotation() {
        return getRotationForValue(rawValue);
    }

    public int getRawValue() {
        return rawValue;
    }

    public String getCellType() {
        return "";
    }

    public String getCellInfo() {
        return "Location: (" + col + ", " + row + ")";
    }

    private int getResourceIDForValue(int rawValue) {
        GridCellImageMapper map = new GridCellImageMapper();
        return map.getImageResourceForCell(rawValue,terrainValue); // TODO OLD
    }

    private int getRotationForValue(int rawValue) {
        if (rawValue > 10000000 & rawValue < 20000000) {
            // Extract the last digit
            int lastDigit = rawValue % 10;

            // Map the last digit to rotation angle
            switch (lastDigit) {
                case 0:
                    return 0; // No rotation for up
                case 2:
                    return 90; // 90 degrees for right
                case 4:
                    return 180; // 180 degrees for down
                case 6:
                    return 270; // 270 degrees for left
                default:
                    return 0; // Default rotation
            }
        }
        return 0;
    }
}