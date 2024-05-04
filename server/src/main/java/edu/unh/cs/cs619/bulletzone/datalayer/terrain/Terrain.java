package edu.unh.cs.cs619.bulletzone.datalayer.terrain;

import edu.unh.cs.cs619.bulletzone.model.Direction;

public interface Terrain {
    int presentItem = 0;
    Direction dir = Direction.Above;

    double getDifficulty(Object entity);

    double getrescourceValue();
    int getIntValue();

    public int getPresentItem();

    public void setPresentItem(int presentItem);

    public boolean isEnterable();

    public Direction getDirection();
}
