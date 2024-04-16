package edu.unh.cs.cs619.bulletzone.datalayer.terrain;

public interface Terrain {
    int presentItem = 0;

    double getDifficulty(Object entity);

    double getrescourceValue();
    int getIntValue();

}
