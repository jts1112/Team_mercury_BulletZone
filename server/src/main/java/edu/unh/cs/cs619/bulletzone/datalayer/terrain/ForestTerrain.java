package edu.unh.cs.cs619.bulletzone.datalayer.terrain;

import edu.unh.cs.cs619.bulletzone.model.FieldEntity;

/**
 * Meadow class that will
 */
public class ForestTerrain extends FieldEntity implements Terrain{
    int presentItem; // present item in the terrain.

    public ForestTerrain(){

    }

    public ForestTerrain(int presentItem){
        this.presentItem = presentItem;
    }

    /**
     * Serializes the current {@link FieldEntity} instance.
     *
     * @return Integer representation of the current {@link FieldEntity}
     */
    @Override
    public int getIntValue() {
        return 4000 + presentItem;
    }

    public void setPresentItem(int presentItem) {
        this.presentItem = presentItem;
    }

    public int getPresentItem() {
        return presentItem;
    }

    @Override
    public FieldEntity copy() {
        return null;
    }

    @Override
    public double getTankDifficulty() {
        return 2;
    }

    @Override
    /**
     * Miners arent allowed to enter forest
     */
    public double getMinerDifficulty() {
        return -1;
    }
}
