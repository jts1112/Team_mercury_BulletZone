package edu.unh.cs.cs619.bulletzone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.greenrobot.eventbus.EventBus;

import edu.unh.cs.cs619.bulletzone.model.events.SpawnEvent;

public final class Game {
    /**
     * Field dimensions
     */
    private static final int FIELD_DIM = 16;
    private final long id;
//    private final ArrayList<FieldHolder> holderGrid = new ArrayList<>(); // TODO removed
    private final GameBoard gameBoard = new GameBoard(FIELD_DIM);

    private final ConcurrentMap<Long, Tank> tanks = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Long> playersIP = new ConcurrentHashMap<>();

    public Game() {
        this.id = 0;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    @JsonIgnore
    public ArrayList<FieldHolder> getHolderGrid() {
        return gameBoard.getBoard();
    }

    public void addTank(String ip, Tank tank) {
        synchronized (tanks) {
            tanks.put(tank.getId(), tank);
            playersIP.put(ip, tank.getId());
        }
        EventBus.getDefault().post(new SpawnEvent(tank.getIntValue(), tank.getPosition()));
    }

    public Tank getTank(Long tankId) {
        return tanks.get(tankId);
    }

    public ConcurrentMap<Long, Tank> getTanks() {
        return tanks;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    // TODO can be removed since in gameBoard
//    public List<Optional<FieldEntity>> getGrid() {
//        synchronized (holderGrid) {
//            List<Optional<FieldEntity>> entities = new ArrayList<>();
//
//            FieldEntity entity;
//            for (FieldHolder holder : holderGrid) {
//                if (holder.isPresent()) {
//                    entity = holder.getEntity();
//                    entity = entity.copy();
//
//                    entities.add(Optional.of(entity));
//                } else {
//                    entities.add(Optional.empty());
//                }
//            }
//            return entities;
//        }
//    }

    public Tank getTank(String ip){
        if (playersIP.containsKey(ip)){
            return tanks.get(playersIP.get(ip));
        }
        return null;
    }

    public void removeTank(long tankId){
        synchronized (tanks) {
            Tank t = tanks.remove(tankId);
            if (t != null) {
                playersIP.remove(t.getIp());
            }
        }
    }

    // TODO can be removed since now in gameboard class
//    public int[][] getGrid2D() {
//        int[][] grid = new int[FIELD_DIM][FIELD_DIM];
//
//        synchronized (holderGrid) {
//            FieldHolder holder;
//            for (int i = 0; i < FIELD_DIM; i++) {
//                for (int j = 0; j < FIELD_DIM; j++) {
//                    holder = holderGrid.get(i * FIELD_DIM + j);
//                    if (holder.isPresent()) {
//                        grid[i][j] = holder.getEntity().getIntValue();
//                    } else {
//                        grid[i][j] = 0;
//                    }
//                }
//            }
//        }
//
//        return grid;
//    }
}
