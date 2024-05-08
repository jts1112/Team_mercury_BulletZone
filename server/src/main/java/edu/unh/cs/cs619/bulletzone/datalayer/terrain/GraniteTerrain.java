package edu.unh.cs.cs619.bulletzone.datalayer.terrain;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Vehicle;

/**
 * Exactly like forest, will block bullets. But different image.
 */
public class GraniteTerrain implements Terrain{
    int presentItem; // present item in the terrain.

    public GraniteTerrain(){ }

    public GraniteTerrain(int presentItem){
        this.presentItem = presentItem;
    }

    /**
     * Serializes the current {@link FieldEntity} instance.
     *
     * @return Integer representation of the current {@link FieldEntity}
     */
    @Override
    public int getIntValue() {
        return 10000 + presentItem;
    }

    public void setPresentItem(int presentItem) {
        this.presentItem = presentItem;
    }

    public int getPresentItem() {
        return presentItem;
    }

    @Override
    public double getDifficulty(Object entity) {
        if (entity instanceof Vehicle){
            Vehicle vehicle = (Vehicle) entity;
            if (vehicle.isTracked()) {
                return 2;
            } else if (vehicle.isWheeled()) {
                return -1;
            }
        }
        return -1;
    }

    @Override
    public double getrescourceValue() {
        return 0;
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
