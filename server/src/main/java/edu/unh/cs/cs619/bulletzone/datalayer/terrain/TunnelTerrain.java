package edu.unh.cs.cs619.bulletzone.datalayer.terrain;

import edu.unh.cs.cs619.bulletzone.model.Direction;

public class TunnelTerrain implements Terrain {
    int presentItem; // present item in the terrain.

    public TunnelTerrain(){
        presentItem = 0;
    }

    public TunnelTerrain(int presentItem) {
        this.presentItem = presentItem;
    }

    @Override
    public int getIntValue() {
        return 5000 + presentItem;
    }

    @Override
    public int getPresentItem() {
        return presentItem;
    }

    @Override
    public void setPresentItem(int presentItem) {
        this.presentItem = presentItem;
    }

    @Override
    public double getDifficulty(Object entity) {
        return 1;
    }

    @Override
    public double getrescourceValue() {
        return 2;
    }

    @Override
    public boolean isEnterable() {
        return false;
    }

    @Override
    public Direction getDirection() {
        return null;
    }
}
