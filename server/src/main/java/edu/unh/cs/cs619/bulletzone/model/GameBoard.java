package edu.unh.cs.cs619.bulletzone.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameBoard {
    private Game game;
    private ArrayList<FieldHolder> board = new ArrayList<>();

    int fieldDimension;

    public GameBoard(int feildDimension){
        fieldDimension = feildDimension;
    }

    /**
     * Game board CreateFieldHolderGrid that was originally in InMemoryGameRepository
     * Creates the fieldholderGrid with the specified dimensions
     *
     * @param monitor monitor object needed to be passed
     * @return the GameBoardBuilder instance
     */
    public void createFieldHolderGrid(Object monitor) {

        synchronized (monitor) {
            if (board != null){
                board.clear();
            } else {
                board = new ArrayList<FieldHolder>();
            }

            for (int i = 0; i < fieldDimension * fieldDimension; i++) {
                board.add(new FieldHolder(i));
            }

            FieldHolder targetHolder;
            FieldHolder rightHolder;
            FieldHolder downHolder;

            // Build connections
            for (int i = 0; i < fieldDimension; i++) {
                for (int j = 0; j < fieldDimension; j++) {
                    targetHolder = board.get(i * fieldDimension + j);
                    rightHolder = board.get(i * fieldDimension
                            + ((j + 1) % fieldDimension));
                    downHolder = board.get(((i + 1) % fieldDimension)
                            * fieldDimension + j);

                    targetHolder.addNeighbor(Direction.Right, rightHolder);
                    rightHolder.addNeighbor(Direction.Left, targetHolder);

                    targetHolder.addNeighbor(Direction.Down, downHolder);
                    downHolder.addNeighbor(Direction.Up, targetHolder);
                }
            }
        }
    }

    public List<Optional<FieldEntity>> getGrid() {
        synchronized (board) {
            List<Optional<FieldEntity>> entities = new ArrayList<>();

            FieldEntity entity;
            for (FieldHolder holder : board) {
                if (holder.isPresent()) {
                    entity = holder.getEntity();
                    entity = entity.copy();

                    entities.add(Optional.of(entity));
                } else {
                    entities.add(Optional.empty());
                }
            }
            return entities;
        }
    }

    public void setBoard(ArrayList<FieldHolder> validGrid){
        board = validGrid;
    }

    public ArrayList<FieldHolder> getBoard() {
        return board;
    }

    /**
     * Sets the destructive wall with no destruct value or position assigned.
     *
     * @param index index of the wall to be set
     * @return the GameBoardBuilderInstance
     */
    public void setWall(int index) {
        board.get(index).setFieldEntity(new Wall());
    }

    /**
     * Sets the destructive wall with destruct value of 1500
     * specified index in the field holder grid
     *
     * @param index the index of the field holder grid
     * @return the GameBoardBuilder instance
     */
    public void setWall(int destructValue,int index){
        board.get(index).setFieldEntity(new Wall(destructValue, index));
    }

    public FieldHolder getGameboardCell(int index){
        return board.get(index);
    }

    public int[][] getTerrain2DGrid(){
        int[][] grid = new int[fieldDimension][fieldDimension];

        synchronized (board) {
            FieldHolder holder;
            for (int i = 0; i < fieldDimension; i++) {
                for (int j = 0; j < fieldDimension; j++) {
                    grid[i][j] = board.get(i * fieldDimension + j).getTerrain().getIntValue();

                }
            }
        }

        return grid;
    }

    public int[][] getGrid2D() {
        int[][] grid = new int[fieldDimension][fieldDimension];

        synchronized (board) {
            FieldHolder holder;
            for (int i = 0; i < fieldDimension; i++) {
                for (int j = 0; j < fieldDimension; j++) {
                    holder = board.get(i * fieldDimension + j);
                    if (holder.isPresent()) {
                        grid[i][j] = holder.getEntity().getIntValue();
                    } else {
                        grid[i][j] = 0;
                    }
                }
            }
        }

        return grid;
    }

    public GameBoard initialize(){
       return null;
    }
}
