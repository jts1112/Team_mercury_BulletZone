package edu.unh.cs.cs619.bulletzone.datalayer.terrain;

public class EntranceTerrain implements Terrain {
    int presentItem; // present item in the terrain.

    public EntranceTerrain(){
        presentItem = 0;
    }

    public EntranceTerrain(int presentItem){
        this.presentItem = presentItem;
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
    public double getDifficulty(Object entity) {
        return 1;
    }

    @Override
    public double getrescourceValue() {
        return 0;
    }
}
