package edu.unh.cs.cs619.bulletzone.datalayer.terrain;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Vehicle;

/**
 * Meadow class that will
 */
public class RockyTerrain implements Terrain{
    int presentItem; // present item in the terrain.

    public RockyTerrain(){
        presentItem = 0;
    }

    public RockyTerrain(int presentItem){
        this.presentItem = presentItem;
    }

    /**
     * Serializes the current {@link FieldEntity} instance.
     *
     * @return Integer representation of the current {@link FieldEntity}
     */
    @Override

    public int getIntValue() {
        return 2000 + presentItem;
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
                return 1.5;
            }
        }
        return -1;
    }

    @Override
    public double getrescourceValue() {
        return 10;
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
