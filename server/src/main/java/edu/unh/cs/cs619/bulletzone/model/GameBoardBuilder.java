/**
 * This class represents a builder for constructing a game board grid.
 * It provides methods to set up the grid with various entities such as walls.
 */

package edu.unh.cs.cs619.bulletzone.model;

import java.util.ArrayList;
import java.util.Random;

import edu.unh.cs.cs619.bulletzone.datalayer.terrain.EntranceTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.ForestTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.GemTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.HillsTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.IronTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.MeadowTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.RockyTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.Terrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.TunnelTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.UbontiumTerrain;
import edu.unh.cs.cs619.bulletzone.datalayer.terrain.*;
import edu.unh.cs.cs619.bulletzone.model.entities.DirtRock;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.StreakedDirt;
import edu.unh.cs.cs619.bulletzone.model.entities.Wall;

public class GameBoardBuilder {

    private ArrayList<FieldHolder>fieldHolderGrid = new ArrayList<>();
    int fieldDimension;
    Object monitor;

    /**
     * Initial constructor
     */
    public GameBoardBuilder(int fieldDimension,Object monitor) {

        // initializeing the grid by creating the field holder grid.
        createFieldHolderGrid(fieldDimension, monitor);
        this.fieldDimension = fieldDimension;
        this.monitor = monitor;
    }

    /**
     * sets the field holder grid to the specified field holder grid.
     *
     * @param fieldHolderGrid the field holder grid to set.
     * @return returns the Gameboardbuilder instance.
     */
    public GameBoardBuilder setFieldHolderGrid(ArrayList<FieldHolder>fieldHolderGrid){
        this.fieldHolderGrid = fieldHolderGrid;
        return this;
}

    /**
     * Sets the destructive wall with destruct value of 1500
     * specified index in the field holder grid
     *
     * @param index the index of the field holder grid
     * @return the GameBoardBuilder instance
     */
    public GameBoardBuilder setWall(int destructValue,int index) {
        Wall wall = new Wall(destructValue, index);
        fieldHolderGrid.get(index).setFieldEntity(wall);
        wall.setParent(fieldHolderGrid.get(index));
        return this;
    }

    /**
     * Sets the destructive wall with no destruct value or position assigned.
     *
     * @param index index of the wall to be set
     * @return the GameBoardBuilderInstance
     */
    public GameBoardBuilder setWall(int index) {
        Wall wall = new Wall();
        fieldHolderGrid.get(index).setFieldEntity(wall);
        wall.setParent(fieldHolderGrid.get(index));
        return this;
    }

    public GameBoardBuilder setStreakedDirt(int index) {
        StreakedDirt streakedDirt = new StreakedDirt(index);
        fieldHolderGrid.get(index).setFieldEntity(streakedDirt);
        streakedDirt.setParent(fieldHolderGrid.get(index));
        return this;
    }

    /**
     * Sets the destructive dirt with destruct value
     * specified index in the field holder grid
     *
     * @param index the index of the field holder grid
     * @return the GameBoardBuilder instance
     */
    public GameBoardBuilder setDirt(int destructValue,int index) {
        DirtRock dirt = new DirtRock(destructValue, index);
        fieldHolderGrid.get(index).setFieldEntity(dirt);
        dirt.setParent(fieldHolderGrid.get(index));
        return this;
    }

    public GameBoardBuilder setRock(int index) {
        DirtRock rock = new DirtRock();
        fieldHolderGrid.get(index).setFieldEntity(rock);
        rock.setParent(fieldHolderGrid.get(index));
        return this;
    }

    public GameBoardBuilder setRockyTerrain(int index) {
        fieldHolderGrid.get(index).setTerrain(new RockyTerrain());
        return this;
    }

    public GameBoardBuilder setForestTerrain(int index) {
        fieldHolderGrid.get(index).setTerrain(new ForestTerrain());
        return this;
    }

    public GameBoardBuilder setMeadowTerrain(int index) {
        fieldHolderGrid.get(index).setTerrain(new MeadowTerrain());
        return this;
    }

    public GameBoardBuilder setTunnelTerrain(int index) {
        fieldHolderGrid.get(index).setTerrain(new TunnelTerrain());
        return this;
    }

    public GameBoardBuilder setHillsTerrain(int index) {
        fieldHolderGrid.get(index).setTerrain(new HillsTerrain());
        return this;
    }

    public GameBoardBuilder setEntranceTerrain(int index) {
        fieldHolderGrid.get(index).setTerrain(new EntranceTerrain(Direction.Below));
        fieldHolderGrid.get(index + (fieldDimension * fieldDimension)).setTerrain(new EntranceTerrain(Direction.Above));
        return this;
    }

    public GameBoardBuilder setIronTerrain(int index) {
        fieldHolderGrid.get(index).setTerrain(new IronTerrain());
        return this;
    }

    public GameBoardBuilder setGemTerrain(int index) {
        fieldHolderGrid.get(index).setTerrain(new GemTerrain());
        return this;
    }

    public GameBoardBuilder setUbontiumTerrain(int index) {
        fieldHolderGrid.get(index).setTerrain(new UbontiumTerrain());
        return this;
    }

    public GameBoardBuilder setGraniteTerrain(int index) {
        fieldHolderGrid.get(index).setTerrain(new GraniteTerrain());
        return this;
    }

    /**
     * Sets the row of a terrain to a certain terrain type. [0 - meadow][1 - rocky][2 - hilly][3 - forest][4> - meadow]
     * @param rowNumber
     * @param terrainType
     */
    public GameBoardBuilder setRowTerrain(int rowNumber,int terrainType){

        for (int i = 0 ; i<fieldDimension;i++){
            int index = rowNumber * fieldDimension + i;
            if (terrainType == 1) { // rocky terrain
                setRockyTerrain(index);
            } else if (terrainType == 2) { // hilly terrain
                setHillsTerrain(index);
            } else if (terrainType == 3){ // forest terrain
                setForestTerrain(index);
            } else { // meadow
                setMeadowTerrain(index);
            }
        }
        return this;
    }

    /**
     * Sets the row of a terrain to a certain terrain type with the specified layer
     * @param layer
     * @param rowNumber
     * @param terrainType
     */
    public GameBoardBuilder setLayerRowTerrain(int layer, int rowNumber, int terrainType){

        for (int i = 0; i < fieldDimension; i++){
            int index = (layer * fieldDimension * fieldDimension) + rowNumber * fieldDimension + i;
            if (terrainType == 1) { // rocky terrain
                setRockyTerrain(index);
            } else if (terrainType == 2) { // hilly terrain
                setHillsTerrain(index);
            } else if (terrainType == 3) { // forest terrain
                setForestTerrain(index);
            } else if (terrainType == 4) { // tunnel
                setTunnelTerrain(index);
            } else if (terrainType == 5) { // walls
                setGraniteTerrain(index);
            } else if (terrainType == 6){ // iron terrain.
                setIronTerrain(index);
            }else if (terrainType ==7){ // gem terrain
                setGemTerrain(index);
            } else if(terrainType == 8){ // ubontium terrain
                setUbontiumTerrain(index);
            }else {
                setMeadowTerrain(index);
            }
        }
        return this;
    }

    public GameBoardBuilder setLayerColumnTerrain(int layer, int columnNumber, int terrainType) {
        for (int i = 0; i < fieldDimension; i++) {
            int index = (layer * fieldDimension * fieldDimension) + i * fieldDimension + columnNumber;

            if (terrainType == 1) { // rocky terrain
                setRockyTerrain(index);
            } else if (terrainType == 2) { // hilly terrain
                setHillsTerrain(index);
            } else if (terrainType == 3) { // forest terrain
                setForestTerrain(index);
            } else if (terrainType == 4) { // tunnel
                setTunnelTerrain(index);
            } else if (terrainType == 5) { // walls
                setGraniteTerrain(index);
            } else if (terrainType == 6){ // iron terrain.
                setIronTerrain(index);
            }else if (terrainType ==7){ // gem terrain
                setGemTerrain(index);
            } else if(terrainType == 8){ // ubontium terrain
                setUbontiumTerrain(index);
            }else {
                setMeadowTerrain(index);
            }
        }
        return this;
    }

    /**
     * Game board CreateFieldHolderGrid that was originally in InMemoryGameRepository
     * Creates the fieldholderGrid with the specified dimensions
     *
     * @param fieldDimension specified dimensions of the fieldholder grid
     * @param monitor monitor object needed to be passed
     * @return the GameBoardBuilder instance
     */
    private void createFieldHolderGrid(int fieldDimension, Object monitor) {

        synchronized (monitor) {
            if (this.fieldHolderGrid != null){
                fieldHolderGrid.clear();
            } else {
                this.fieldHolderGrid = new ArrayList<FieldHolder>();
            }

            for (int i = 0; i < (fieldDimension * fieldDimension) * 3; i++) {
                fieldHolderGrid.add(new FieldHolder(i));
                fieldHolderGrid.get(i).setTerrain(new RockyTerrain());

                // TODO trying to make all terrain. remove set terrain once done
            }

            FieldHolder targetHolder;
            FieldHolder rightHolder;
            FieldHolder downHolder;
            FieldHolder belowHolder;

            // Build connections
            for (int k = 0; k < 3; k++) {
                for (int i = 0; i < fieldDimension; i++) {
                    for (int j = 0; j < fieldDimension; j++) {
                        targetHolder = fieldHolderGrid.get((k * fieldDimension * fieldDimension) + i * fieldDimension + j);
                        rightHolder = fieldHolderGrid.get((k * fieldDimension * fieldDimension) + i * fieldDimension
                                + ((j + 1) % fieldDimension));
                        downHolder = fieldHolderGrid.get((k * fieldDimension * fieldDimension) + ((i + 1) % fieldDimension)
                                * fieldDimension + j);

                        if (k < 2) {
                            belowHolder = fieldHolderGrid.get(((k + 1) * fieldDimension * fieldDimension) + i * fieldDimension + j);
                            targetHolder.addNeighbor(Direction.Below, belowHolder);
                            belowHolder.addNeighbor(Direction.Above, targetHolder);
                        }

                        targetHolder.addNeighbor(Direction.Right, rightHolder);
                        rightHolder.addNeighbor(Direction.Left, targetHolder);

                        targetHolder.addNeighbor(Direction.Down, downHolder);
                        downHolder.addNeighbor(Direction.Up, targetHolder);
                    }
                }
            }
        }
//        return this;
    }


    /**
     * The build builds the game board to the specified set
     * @return the constructed fieldholder grid
     */
    public ArrayList<FieldHolder> build() {
        return this.fieldHolderGrid;
    }


    /**
     * Initialization that occurred within the InMemoryGameRepository
     * @return the updated game
     */
    public GameBoardBuilder inMemoryGameRepositoryInitialize(){
        // Test // TODO Move to more appropriate place (and if desired, integrate map loader)
        GameBoardBuilder newBoard = new GameBoardBuilder(fieldDimension,monitor);
        int layerTwo = 2;
        int entranceIndex = 101;
        ArrayList<Integer> takenIndices = new ArrayList<>();
        takenIndices.add(entranceIndex);
        for (int i = 0; i < fieldDimension; i++) {
            int squared = fieldDimension * fieldDimension - fieldDimension;
            takenIndices.add(i);
            takenIndices.add(i * 16);
            takenIndices.add(i * 16 + 15);
            takenIndices.add(squared + i);
        }
        int[] wallIndices = {1, 17, 34, 35, 51, 5, 21, 37, 53, 7, 23, 39, 8, 40, 9, 41, 73};
        int[] destructibleWallIndices = {33, 106, 69, 71, 72};

        for (int index : wallIndices) {
            newBoard.setWall(index);
        }

        for (int index : destructibleWallIndices) {
            newBoard.setWall(1100, index);
        }

        for (int row = 0; row <= 7; row++) {
            newBoard.setRowTerrain(row, 4);
        }

        for (int row = 8; row <= 13; row++) {
            newBoard.setRowTerrain(row, 1);
        }

        newBoard.setRowTerrain(15, 3);

        int[] forestIndices = {66, 67, 68};
        for (int index : forestIndices) {
            newBoard.setForestTerrain(index);
        }

        for (int row = 0; row <= 15; row++) {
            newBoard.setLayerRowTerrain(1, row, 4);
        }

        for (int row = 0; row <= 15; row++) {
            newBoard.setLayerRowTerrain(layerTwo, row, 4);
        }

        newBoard.setEntranceTerrain(entranceIndex);
        Random random = new Random();
        int numIronTerrains = 18 + random.nextInt(29);
        int numGemTerrains = 6 + random.nextInt(10);
        int numUbontiumTerrains = 1 + random.nextInt(5);
        for (int i = 0; i < numIronTerrains; i++) {
            int index = entranceIndex;
            while (takenIndices.contains(index)) {
                index = random.nextInt(fieldDimension * fieldDimension);
            }
            takenIndices.add(index);
            int layeredIndex = (fieldDimension * fieldDimension) + index;
            newBoard.setIronTerrain(layeredIndex);
            newBoard.setStreakedDirt(layeredIndex);
        }
        for (int i = 0; i < numGemTerrains; i++) {
            int index = entranceIndex;
            while (takenIndices.contains(index)) {
                index = random.nextInt(fieldDimension * fieldDimension);
            }
            takenIndices.add(index);
            int layeredIndex = (fieldDimension * fieldDimension) + index;
            newBoard.setGemTerrain(layeredIndex);
            newBoard.setStreakedDirt(layeredIndex);
        }
        for (int i = 0; i < numUbontiumTerrains; i++) {
            int index = entranceIndex;
            while (takenIndices.contains(index)) {
                index = random.nextInt(fieldDimension * fieldDimension);
            }
            takenIndices.add(index);
            int layeredIndex = (fieldDimension * fieldDimension) + index;
            newBoard.setUbontiumTerrain(layeredIndex);
            newBoard.setStreakedDirt(layeredIndex);
        }
        fieldHolderGrid = newBoard.build();
        return this;
    }

    /**
     * Initialization that occurred within the InMemoryGameRepository
     * @return the updated game
     */
    public GameBoardBuilder initializeFieldOfWalls(){
        // Test // TODO Move to more appropriate place (and if desired, integrate map loader)
        for (int i = 1; i <= 256; i++) {
            fieldHolderGrid.get(1).setFieldEntity(new Wall());
        }

        return this;
    }

}
