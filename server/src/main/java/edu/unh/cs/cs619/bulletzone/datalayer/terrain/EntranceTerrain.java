package edu.unh.cs.cs619.bulletzone.datalayer.terrain;

import edu.unh.cs.cs619.bulletzone.model.Direction;

public class EntranceTerrain implements Terrain {
    int presentItem; // present item in the terrain.
    Direction direction; // direction of tunnel

    public EntranceTerrain(){
        presentItem = 0;
    }

    public EntranceTerrain(Direction dir){
        this.direction = dir;
    }

    @Override
    public int getIntValue() {
        return 6000;
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
    public boolean isEnterable() {
        return true;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public double getDifficulty(Object entity) {
        return 1;
    }

    @Override
    public double getrescourceValue() {
        return 0;
    }
}
