package edu.unh.cs.cs619.bulletzone.ui;

import android.content.Context;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import edu.unh.cs.cs619.bulletzone.R;

@EBean
public class GridCellImageMapper {


    public GridCellImageMapper() {

    }

    public int getTerrainImageResource(int cellValue) {
        int imageResource = R.drawable.grass_base1; // Default meadow image resource

        // Map cell values to image resources
        if (cellValue > 0) {
            if ((cellValue >= 2000 && cellValue < 3000)) {
                imageResource = R.drawable.rocky1;
            } else if ((cellValue >= 3000 && cellValue < 4000)) {
                imageResource = R.drawable.hills1;
            } else if ((cellValue >= 4000 && cellValue < 5000)) {
                imageResource = R.drawable.forest1;
            }
        }

        return imageResource;
    }

    public int getEntityImageResource(int cellValue) {
        int imageResource = 0;

        if (cellValue == 1000) {
            imageResource = R.drawable.wall5;
        } else if ((cellValue > 1000 && cellValue < 2000)) {
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

        return imageResource;
    }
}

