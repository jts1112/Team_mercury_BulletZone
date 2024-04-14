package edu.unh.cs.cs619.bulletzone.ui;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import edu.unh.cs.cs619.bulletzone.R;

@EBean
public class GridCellImageMapper {


    public GridCellImageMapper() {

    }

    public int getImageResourceForCell(int cellValue, int terrainvalue) {
        int imageResource = R.drawable.grass_base1; // Default image resource

        // Map cell values to image resources
        if (cellValue > 0) {
            if (cellValue == 1000) {
                imageResource = R.drawable.wall5;
            }
            if ((cellValue > 1000 && cellValue <= 2000)) {
                imageResource = R.drawable.wall2;
            } else if (cellValue >= 2000000 && cellValue <= 3000000) {
                imageResource = R.drawable.bullet1;
            } else if (cellValue >= 10000000 && cellValue <= 20000000) {
                imageResource = R.drawable.tank_icon2;
            } else if (cellValue >= 20000000 && cellValue <= 30000000) {
                imageResource = R.drawable.miner1;
            } else if (cellValue >= 30000000 && cellValue <= 40000000) {
                imageResource = R.drawable.dropship1;
            }
        }
        if (cellValue == 0) {
            Log.d("TerrainValue", String.valueOf(terrainvalue));
            if (terrainvalue >= 2000 && terrainvalue < 3000 ) { // rocky resource
                imageResource = R.drawable.sandterrain1;
            } else if (terrainvalue >= 3000 && terrainvalue < 4000) { // hills resource
                imageResource = R.drawable.hills1;
            } else if (terrainvalue >= 4000 && terrainvalue < 5000) { // forest resource
                imageResource = R.drawable.forest1;
            } else {
                // else its a meadow terrain.
                imageResource = R.drawable.grass_base1;
            }
        }

        return imageResource;
    }
}

