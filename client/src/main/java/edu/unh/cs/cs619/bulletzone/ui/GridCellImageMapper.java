package edu.unh.cs.cs619.bulletzone.ui;
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
        } else if ((cellValue > 1000 && cellValue < 2000)) {
            imageResource = R.drawable.wall2;
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
        } else if (cellValue >= 10_000_000 && cellValue <= 20_000_000) {
            long tankId = (cellValue - 10_000_000) / 10_000;
            if (Ids.getTankIdQueue().contains( (long) tankId)) {
                imageResource = getTankImageResource(tankId);
            } else {
                imageResource = R.drawable.tank_icon_enemy;
            }
        } else if (cellValue >= 20_000_000 && cellValue <= 30_000_000) {
            long minerId = (cellValue - 20_000_000) / 10_000;
            if (Ids.getMinerIdQueue().contains( (long) (cellValue - 20_000_000) / 10_000)) {
                imageResource = getMinerImageResource(minerId);
            } else {
                imageResource = R.drawable.miner_icon_enemy;
            }
        } else if (cellValue >= 30_000_000 && cellValue <= 40_000_000) {
            if (Ids.getDropshipId() == (cellValue - 30_000_000) / 10_000) {
                imageResource = R.drawable.dropship1;
            } else {
                imageResource = R.drawable.dropship2;
            }
        }

        if (powerUp == 1) {
            imageResource = R.drawable.thingamajig1;
        } else if (powerUp == 2)  {
            imageResource = R.drawable.antigrav1;
        } else if (cellValue == 3)  {
            imageResource = R.drawable.fusionreactor1;
        }
        return imageResource;
    }

    public int getTankImageResource(long tankId) {
        Integer resourceId = Ids.tankImageResources.get(tankId);
        if (resourceId != null) {
            return resourceId;
        } else {
            return R.drawable.tank_icon_enemy;
        }
    }

    public int getMinerImageResource(long minerId) {
        Integer resourceId = Ids.minerImageResources.get(minerId);
        if (resourceId != null) {
            return resourceId;
        } else {
            return R.drawable.miner_icon_enemy;
        }
    }
}
