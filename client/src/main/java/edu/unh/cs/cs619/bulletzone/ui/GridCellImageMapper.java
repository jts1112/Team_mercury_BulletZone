package edu.unh.cs.cs619.bulletzone.ui;

import android.content.Context;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import edu.unh.cs.cs619.bulletzone.R;

@EBean
public class GridCellImageMapper {


    public GridCellImageMapper() {

    }

    public int getImageResourceForCell(int cellValue) {
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
            }
        }

        return imageResource;
    }
}

