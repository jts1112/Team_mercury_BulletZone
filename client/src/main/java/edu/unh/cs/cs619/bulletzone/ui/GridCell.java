package edu.unh.cs.cs619.bulletzone.ui;

import android.util.Log;

public class GridCell {
    private int terrainResourceID;
    private int entityResourceID;
    private int entityRotation;
    private int row;
    private int col;

    public GridCell(int terrainValue, int entityValue, int row, int col) {
        this.terrainResourceID = terrainValue;
        this.entityResourceID = entityValue;
        this.entityRotation = 0;
        this.row = row;
        this.col = col;
    }

    public int getTerrainResourceID() {
        return terrainResourceID;
    }

    public int getEntityResourceID() {
        return entityResourceID;
    }

    public int getEntityRotation() {
        return entityRotation;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRotationForValue(int rawValue) {
        if (rawValue > 10000000 & rawValue < 40000000) {
            // Extract the last digit
            int lastDigit = rawValue % 10;
            // Map the last digit to rotation angle
            switch (lastDigit) {
                case 0:
                    this.entityRotation = 0; // No rotation for up
                    break;
                case 2:
                    this.entityRotation = 90; // 90 degrees for right
                    break;
                case 4:
                    this.entityRotation = 180; // 180 degrees for down
                    break;
                case 6:
                    this.entityRotation = 270; // 270 degrees for left
                    break;
            }
        }
    }
}