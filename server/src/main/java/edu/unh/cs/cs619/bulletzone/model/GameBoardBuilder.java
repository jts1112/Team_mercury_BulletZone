/**
 * This class represents a builder for constructing a game board grid.
 * It provides methods to set up the grid with various entities such as walls.
 */

package edu.unh.cs.cs619.bulletzone.model;

import com.google.common.util.concurrent.Monitor;

import java.util.ArrayList;

public class GameBoardBuilder {

    private ArrayList<FieldHolder>fieldHolderGrid;

    /**
     * Initial constructor
     */
    public GameBoardBuilder() {
    }

    /**
     * sets the feild holder grid to the specified field holder grid.
     *
     * @param fieldHolderGrid the feild holder grid to set.
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
    public GameBoardBuilder setDestructWall(int index){
        fieldHolderGrid.get(index).setFieldEntity(new Wall(1500, index));
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
     * Game board CreateFeildHolderGrid that was originally in InMemoryGameRepository
     * Creates the feildholderGrid with the specified dimensions
     *
     * @param fieldDimension specified dimensions of the feildholder grid
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
     * Initialization that occured within the InMemoryGameRepository
     * @return the updated game
     */
    public GameBoardBuilder inMemoryGameReposiryInitialize(){
        // Test // TODO Move to more appropriate place (and if desired, integrate map loader)
        fieldHolderGrid.get(1).setFieldEntity(new Wall());
        fieldHolderGrid.get(2).setFieldEntity(new Wall());
        fieldHolderGrid.get(3).setFieldEntity(new Wall());

        fieldHolderGrid.get(17).setFieldEntity(new Wall());
        fieldHolderGrid.get(33).setFieldEntity(new Wall(1500, 33));
        fieldHolderGrid.get(49).setFieldEntity(new Wall(1500, 49));
        fieldHolderGrid.get(65).setFieldEntity(new Wall(1500, 65));

        fieldHolderGrid.get(34).setFieldEntity(new Wall());
        fieldHolderGrid.get(66).setFieldEntity(new Wall(1500, 66));

        fieldHolderGrid.get(35).setFieldEntity(new Wall());
        fieldHolderGrid.get(51).setFieldEntity(new Wall());
        fieldHolderGrid.get(67).setFieldEntity(new Wall(1500, 67));

        fieldHolderGrid.get(5).setFieldEntity(new Wall());
        fieldHolderGrid.get(21).setFieldEntity(new Wall());
        fieldHolderGrid.get(37).setFieldEntity(new Wall());
        fieldHolderGrid.get(53).setFieldEntity(new Wall());
        fieldHolderGrid.get(69).setFieldEntity(new Wall(1500, 69));

        fieldHolderGrid.get(7).setFieldEntity(new Wall());
        fieldHolderGrid.get(23).setFieldEntity(new Wall());
        fieldHolderGrid.get(39).setFieldEntity(new Wall());
        fieldHolderGrid.get(71).setFieldEntity(new Wall(1500, 71));

        fieldHolderGrid.get(8).setFieldEntity(new Wall());
        fieldHolderGrid.get(40).setFieldEntity(new Wall());
        fieldHolderGrid.get(72).setFieldEntity(new Wall(1500, 72));

        fieldHolderGrid.get(9).setFieldEntity(new Wall());
        fieldHolderGrid.get(25).setFieldEntity(new Wall());
        fieldHolderGrid.get(41).setFieldEntity(new Wall());
        fieldHolderGrid.get(57).setFieldEntity(new Wall());
        fieldHolderGrid.get(73).setFieldEntity(new Wall());
        return  this;
    }

}
