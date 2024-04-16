package edu.unh.cs.cs619.bulletzone.ui;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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

    // Constructor to create GridCell object from JSON
    public GridCell(JSONObject json) throws JSONException {
        this.terrainResourceID = json.getInt("terrainResourceID");
        this.entityResourceID = json.getInt("entityResourceID");
        this.entityRotation = json.getInt("entityRotation");
        this.row = json.getInt("row");
        this.col = json.getInt("col");
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
        } else {
            this.entityRotation = 0;
        }
    }

    // Method to convert GridCell object to JSON
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("terrainResourceID", terrainResourceID);
        json.put("entityResourceID", entityResourceID);
        json.put("entityRotation", entityRotation);
        json.put("row", row);
        json.put("col", col);
        return json;
    }
}