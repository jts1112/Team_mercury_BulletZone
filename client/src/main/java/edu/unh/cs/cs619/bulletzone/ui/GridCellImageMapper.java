package edu.unh.cs.cs619.bulletzone.ui;
import android.util.Log;

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
            } else if ((cellValue >= 5000 && cellValue < 6000)) {
                imageResource = R.drawable.tunnel1; // tunnel
                powerUp = cellValue - 5000;
            } else if (cellValue == 6000) {
                imageResource = R.drawable.entrance1; // tunnel
            } else if (cellValue >= 7000 && cellValue < 8000) {
                imageResource = R.drawable.ironterrain;
            } else if (cellValue >= 8000 && cellValue < 9000) {
                imageResource = R.drawable.gemterrain;
            } else if (cellValue >= 9000 && cellValue < 10000) {
                imageResource = R.drawable.unobtaniumterrain;
            } else if (cellValue >= 10000 && cellValue < 11000) {
                imageResource = R.drawable.granite;
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
        int powerUp = 0;

        if (cellValue == 1000) {
            imageResource = R.drawable.wall5;
        } else if ((cellValue > 1000 && cellValue < 2000)) { // destructable wall

            if ((cellValue - 1000) <= 100 && (cellValue - 1000) >= 51){ // full health
                imageResource = R.drawable.wall2;
            } else if ((cellValue - 1000) >= 26 && (cellValue - 1000) <= 50) { // half health
                imageResource = R.drawable.wall2low;
            } else { // 25% health
                imageResource = R.drawable.wall2verylow;
            }

        } else if ((cellValue >= 2000 && cellValue < 3000)) {
            powerUp = cellValue - 2000;
        } else if ((cellValue >= 3000 && cellValue < 4000)) {
            powerUp = cellValue - 3000;
        } else if ((cellValue >= 4000 && cellValue < 5000)) {
            powerUp = cellValue - 4000;
        } else if (cellValue == 5000) {
            imageResource = R.drawable.rock1;
        } else if (cellValue > 5000 && cellValue < 6000) {
            imageResource = R.drawable.dirt1;
        } else if (cellValue == 6000) {
            imageResource = R.drawable.streaked_dirt;
        } else if (cellValue >= 2_000_000 && cellValue <= 3_000_000) {
            imageResource = R.drawable.bullet1;
        } else if (cellValue >= 10_000_000 && cellValue <= 20_000_000) { // Its a tank rescource
            long tankId = (cellValue - 10_000_000) / 10_000;
            int health = (cellValue % 10_000) / 10;

            if (Ids.getTankIdQueue().contains( (long) tankId)) {
                Integer[] imageResources = Ids.getTankImageResource(tankId);
                if (health <= 100 && health >= 51) { // full health
                    imageResource = imageResources[0];
                } else if (health <= 50 && health >= 26) { // half health
                    imageResource = imageResources[1];
                } else { // low health
                    imageResource = imageResources[2];
                }
            } else {
                if (health <= 100 && health >= 51) { // full health
                    imageResource = R.drawable.tank_icon_enemy_full0;
                } else if (health <= 50 && health >= 26) { // half health
                    imageResource = R.drawable.tank_icon_enemy_low0;
                } else { // low health
                    imageResource = R.drawable.tank_icon_enemy_very_low0;
                }
//                imageResource = R.drawable.tank_icon_enemy;
            }
        } else if (cellValue >= 20_000_000 && cellValue <= 30_000_000) { // Its a miner resource
            long minerId = (cellValue - 20_000_000) / 10_000;
            int health = (cellValue % 10_000) / 10;
            if (Ids.getMinerIdQueue().contains( (long) (cellValue - 20_000_000) / 10_000)) {
                Integer[] imageResources = Ids.getMinerImageResource(minerId);

                if (health <= 120 && health >= 61) { // full health
                    imageResource = imageResources[0];
                } else if (health <= 60 && health >= 30) { // half health
                    imageResource = imageResources[1];
                } else { // low health
                    imageResource = imageResources[2];
                }
            } else {
                if (health <= 120 && health >= 61) { // full health
                    imageResource = R.drawable.miner_enemy_full0;
                } else if (health <= 60 && health >= 30) { // half health
                    imageResource = R.drawable.miner_enemy_low0;
                } else { // low health
                    imageResource = R.drawable.miner_enemy_very_low0;
                }
//                imageResource = R.drawable.miner_icon_enemy;
            }
        } else if (cellValue >= 30_000_000 && cellValue <= 40_000_000) { // Its a drop ship rescource
            int health = (cellValue % 10000) / 10;
            if (Ids.getDropshipId() == (cellValue - 30_000_000) / 10_000) {

                if (health <= 300 && health >= 151) { // full health
                    imageResource = R.drawable.dropship1full;
                } else if (health <= 150 && health >= 76) { // half health
                    imageResource = R.drawable.dropship1low;
                } else { // low health
                    imageResource = R.drawable.dropship1verylow;
                }

//                imageResource = R.drawable.dropship1;
            } else {

                if (health <= 300 && health >= 151) { // full health
                    imageResource = R.drawable.dropship2full;
                } else if (health <= 150 && health >= 76) { // half health
                    imageResource = R.drawable.dropship2low;
                } else { // low health
                    imageResource = R.drawable.dropship2verylow;
                }

//                imageResource = R.drawable.dropship2;
            }
        }

        if (powerUp == 1) {
            imageResource = R.drawable.thingamajig1;
        } else if (powerUp == 2)  {
            imageResource = R.drawable.antigrav1;
        } else if (cellValue == 3)  {
            imageResource = R.drawable.fusionreactor1;
        } else if (powerUp == 4){ //  Power Drill powerup set 4
//            Log.d("NewPowerUp", String.valueOf(powerUp));
            imageResource = R.drawable.powereddrill1;
        } else if (powerUp == 5) { // automatic repair kit set 5
            imageResource = R.drawable.repairkit1;
        } else if (powerUp == 6){ // deflector sheild 6
            imageResource = R.drawable.deflectorshield1;
        }
        return imageResource;
    }

}
