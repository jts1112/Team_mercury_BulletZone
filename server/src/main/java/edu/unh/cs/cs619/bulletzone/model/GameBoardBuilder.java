package edu.unh.cs.cs619.bulletzone.model;

import java.util.ArrayList;

public class GameBoardBuilder {

    private ArrayList<FieldHolder>fieldHolderGrid;

    public GameBoardBuilder(ArrayList<FieldHolder> fieldHolderGrid) {
        this.fieldHolderGrid = fieldHolderGrid;
    }


    public GameBoardBuilder setDestructWall(int index){
        fieldHolderGrid.get(index).setFieldEntity(new Wall());
        return this;
    }

    public GameBoardBuilder setWall(int index) {
        fieldHolderGrid.get(53).setFieldEntity(new Wall());
        return this;
    }

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
