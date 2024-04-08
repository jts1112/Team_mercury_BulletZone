/**
 * This class represents a builder for constructing a game board grid.
 * It provides methods to set up the grid with various entities such as walls.
 */

package edu.unh.cs.cs619.bulletzone.model;

import java.util.ArrayList;

public class GameBoardBuilder {

    private ArrayList<FieldHolder>fieldHolderGrid;

    /**
     * Initial constructor
     */
    public GameBoardBuilder() {
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
    public GameBoardBuilder setWall(int destructValue,int index){
        fieldHolderGrid.get(index).setFieldEntity(new Wall(destructValue, index));
        return this;
    }

    /**
     * Sets the destructive wall with no destruct value or position assigned.
     *
     * @param index index of the wall to be set
     * @return the GameBoardBuilderInstance
     */
    public GameBoardBuilder setWall(int index) {
        fieldHolderGrid.get(index).setFieldEntity(new Wall());
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
    public GameBoardBuilder createFieldHolderGrid(int fieldDimension, Object monitor) {

        synchronized (monitor) {
            if (this.fieldHolderGrid != null){
                fieldHolderGrid.clear();
            } else {
                this.fieldHolderGrid = new ArrayList<FieldHolder>();
            }

            for (int i = 0; i < fieldDimension * fieldDimension; i++) {
                fieldHolderGrid.add(new FieldHolder(i));
            }

            FieldHolder targetHolder;
            FieldHolder rightHolder;
            FieldHolder downHolder;

            // Build connections
            for (int i = 0; i < fieldDimension; i++) {
                for (int j = 0; j < fieldDimension; j++) {
                    targetHolder = fieldHolderGrid.get(i * fieldDimension + j);
                    rightHolder = fieldHolderGrid.get(i * fieldDimension
                            + ((j + 1) % fieldDimension));
                    downHolder = fieldHolderGrid.get(((i + 1) % fieldDimension)
                            * fieldDimension + j);

                    targetHolder.addNeighbor(Direction.Right, rightHolder);
                    rightHolder.addNeighbor(Direction.Left, targetHolder);

                    targetHolder.addNeighbor(Direction.Down, downHolder);
                    downHolder.addNeighbor(Direction.Up, targetHolder);
                }
            }
        }
        return this;
    }


    /**
     * The build builds the game board to the specified set
     * @return the constructed fieldholder grid
     */
    public ArrayList<FieldHolder> build(){
        return this.fieldHolderGrid;
    }


    /**
     * Initialization that occurred within the InMemoryGameRepository
     * @return the updated game
     */
    public GameBoardBuilder inMemoryGameReposiryInitialize(){
        // Test // TODO Move to more appropriate place (and if desired, integrate map loader)
        GameBoardBuilder newBoard = new GameBoardBuilder();
        newBoard.setWall(1).
                setWall(2).
                setWall(3).
                setWall(17).
                setWall(1500,33).
                setWall(1500,49).
                setWall(1500,65).
                setWall(34).
                setWall(1500,66).
                setWall(35).
                setWall(51).
                setWall(1500,67).
                setWall(5).
                setWall(21).
                setWall(37).
                setWall(53).
                setWall(1500,69).
                setWall(7).
                setWall(23).
                setWall(39).
                setWall(1500,71).
                setWall(8).
                setWall(40).
                setWall(1500,72).
                setWall(9).
                setWall(25).
                setWall(41).
                setWall(57).
                setWall(73);

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
