package edu.unh.cs.cs619.bulletzone.model;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GameBuilder {

    private static final int FIELD_DIM = 16;
    private long id;
    private ArrayList<FieldHolder> holderGrid = new ArrayList<>();
    private ConcurrentMap<Long, Tank> tanks = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Long> playersIP = new ConcurrentHashMap<>();


    public  GameBuilder settanks(ConcurrentMap<Long, Tank> tanks) {
        this.tanks = tanks;
        return this;
    }

    public GameBuilder setID(long id){
        this.id = id;
        return  this;
    }

    /**
     * Moved code that was in inMemoryGameRepository Create() that initialized all
     * the holdergrid cells
     * @return the modified GameBuilder.
     */
    public GameBuilder initialize(){
        this.holderGrid.get(1).setFieldEntity(new Wall());
        this.holderGrid.get(2).setFieldEntity(new Wall());
        this.holderGrid.get(3).setFieldEntity(new Wall());

        this.holderGrid.get(17).setFieldEntity(new Wall());
        this.holderGrid.get(33).setFieldEntity(new Wall(1500, 33));
        this.holderGrid.get(49).setFieldEntity(new Wall(1500, 49));
        this.holderGrid.get(65).setFieldEntity(new Wall(1500, 65));

        this.holderGrid.get(34).setFieldEntity(new Wall());
        this.holderGrid.get(66).setFieldEntity(new Wall(1500, 66));

        this.holderGrid.get(35).setFieldEntity(new Wall());
        this.holderGrid.get(51).setFieldEntity(new Wall());
        this.holderGrid.get(67).setFieldEntity(new Wall(1500, 67));

        this.holderGrid.get(5).setFieldEntity(new Wall());
        this.holderGrid.get(21).setFieldEntity(new Wall());
        this.holderGrid.get(37).setFieldEntity(new Wall());
        this.holderGrid.get(53).setFieldEntity(new Wall());
        this.holderGrid.get(69).setFieldEntity(new Wall(1500, 69));

        this.holderGrid.get(7).setFieldEntity(new Wall());
        this.holderGrid.get(23).setFieldEntity(new Wall());
        this.holderGrid.get(39).setFieldEntity(new Wall());
        this.holderGrid.get(71).setFieldEntity(new Wall(1500, 71));

        this.holderGrid.get(8).setFieldEntity(new Wall());
        this.holderGrid.get(40).setFieldEntity(new Wall());
        this.holderGrid.get(72).setFieldEntity(new Wall(1500, 72));

        this.holderGrid.get(9).setFieldEntity(new Wall());
        this.holderGrid.get(25).setFieldEntity(new Wall());
        this.holderGrid.get(41).setFieldEntity(new Wall());
        this.holderGrid.get(57).setFieldEntity(new Wall());
        this.holderGrid.get(73).setFieldEntity(new Wall());
        return this;
    }

    public Game build(){
        Game game = new Game();
        game.getHolderGrid().addAll(this.holderGrid);
        // probably need more.

        return game;
    }
}
