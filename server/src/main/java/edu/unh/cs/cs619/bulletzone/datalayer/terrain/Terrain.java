package edu.unh.cs.cs619.bulletzone.datalayer.terrain;

import edu.unh.cs.cs619.bulletzone.model.FieldEntity;

public class Terrain extends FieldEntity {
    int rawID; // raw rescource ID.
    int movement; // movement penalty. not needed in ID.

    int presentItem; // tells what item is on the terrain if any.

    public Terrain(int value){
//        if (){ // meadow clause
//            Meadow hi = new Meadow();
//            hi.
//        } else if () { // hills clause
//
//        } else if () { // rocky
//
//        } else if (){ // forest
//
//        } else { // empty feild entity. with value 0
//
//        }
    }

    /**
     * Serializes the current {@link FieldEntity} instance.
     *
     * @return Integer representation of the current {@link FieldEntity}
     */
    @Override
    public int getIntValue() {
        return 0;
    }

    @Override
    public FieldEntity copy() {
        return null;
    }
}
