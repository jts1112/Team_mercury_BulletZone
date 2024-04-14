package edu.unh.cs.cs619.bulletzone.datalayer.terrain;

import edu.unh.cs.cs619.bulletzone.model.entities.FieldEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Vehicle;

/**
 * Meadow class that will
 */
public class MeadowTerrain implements Terrain{
    int presentItem; // present item in the terrain.

    public MeadowTerrain(){

    }

    public MeadowTerrain(int presentItem){
        this.presentItem = presentItem;
    }

    /**
     * Serializes the current {@link FieldEntity} instance.
     *
     * @return Integer representation of the current {@link FieldEntity}
     */
    @Override
    public int getIntValue() {
        return presentItem;
    }

    public int getPresentItem() {
        return presentItem;
    }

    public void setPresentItem(int presentItem) {
        this.presentItem = presentItem;
    }

    @Override
    public double getDifficulty(Object entity) {
        if (entity instanceof Vehicle){
            Vehicle vehicle = (Vehicle) entity;
            if (vehicle.isTracked()) {
                return 1;
            } else if (vehicle.isWheeled()) {
                return 1;
            }
        }
        return -1;
    }

    @Override
    public double getrescourceValue() {
        return 2;
    }
}
