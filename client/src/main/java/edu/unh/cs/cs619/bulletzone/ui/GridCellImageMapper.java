package edu.unh.cs.cs619.bulletzone.ui;

import org.androidannotations.annotations.EBean;

import edu.unh.cs.cs619.bulletzone.R;
import edu.unh.cs.cs619.bulletzone.util.UnitIds;

public class GridCellImageMapper {

    private static GridCellImageMapper instance;
    private UnitIds Ids;

    private GridCellImageMapper() {
        Ids = UnitIds.getInstance();
    }

    public static GridCellImageMapper getInstance() {
        if (instance == null) {
            synchronized (GridCellImageMapper.class) {
                if (instance == null) {
                    instance = new GridCellImageMapper();
                }
            }
        }
        return instance;
    }

    public int getTerrainImageResource(int cellValue) {
        int imageResource = R.drawable.grass_base1; // Default meadow image resource

        int powerUp = 0;

        // Map cell values to image resources
        if (cellValue > 0) {
            if ((cellValue >= 2000 && cellValue < 3000)) {
                imageResource = R.drawable.rocky1;
                powerUp = cellValue - 2000;
            } else if ((cellValue >= 3000 && cellValue < 4000)) {
                imageResource = R.drawable.hills1;
                powerUp = cellValue - 3000;
            } else if ((cellValue >= 4000 && cellValue < 5000)) {
                imageResource = R.drawable.forest1;
                powerUp = cellValue - 4000;
            }
            if (powerUp == 1) {
                imageResource = R.drawable.thingamajig1;
            } else if (powerUp == 2)  {
                imageResource = R.drawable.antigrav1;
            } else if (cellValue == 3)  {
                imageResource = R.drawable.fusionreactor1;
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
            if (Ids.getTankId() == (cellValue - 10000000) / 10000) {
                imageResource = R.drawable.tank_icon2;
            } else {
                imageResource = R.drawable.tankicon4;
            }
        } else if (cellValue >= 20000000 && cellValue <= 30000000) {
            if (Ids.getMinerId() == (cellValue - 20000000) / 10000) {
                imageResource = R.drawable.miner1;
            } else {
                imageResource = R.drawable.miner2;
            }
        } else if (cellValue >= 30000000 && cellValue <= 40000000) {
            if (Ids.getDropshipId() == (cellValue - 30000000) / 10000) {
                imageResource = R.drawable.dropship1;
            } else {
                imageResource = R.drawable.dropship2;
            }
        }

        return imageResource;
    }
}

