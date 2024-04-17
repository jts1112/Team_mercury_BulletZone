package edu.unh.cs.cs619.bulletzone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.Miner;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
import edu.unh.cs.cs619.bulletzone.model.events.SpawnEvent;

public final class Game {
    /**
     * Field dimensions
     */
    private static final int FIELD_DIM = 16;
    private final long id;
//    private final ArrayList<FieldHolder> holderGrid = new ArrayList<>(); // TODO removed
    private final GameBoard gameBoard = new GameBoard(FIELD_DIM);

    private final ConcurrentMap<Long, Dropship> dropships = new ConcurrentHashMap<>();
    private final ConcurrentMap<Long, Tank> tanks = new ConcurrentHashMap<>();
    private final ConcurrentMap<Long, Miner> miners = new ConcurrentHashMap<>(); // Add this line
    private final ConcurrentMap<String, Long> playersIP = new ConcurrentHashMap<>();

    public Game() {
        this.id = 0;
        EventBus.getDefault().register(this);
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    @JsonIgnore
    public ArrayList<FieldHolder> getHolderGrid() {
        return gameBoard.getBoard();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    // --------------------------------- PlayableEntity ---------------------------------

    public List<PlayableEntity> getPlayableEntities() {
        List<PlayableEntity> playableEntities = new ArrayList<>();
        playableEntities.addAll(tanks.values());
        playableEntities.addAll(dropships.values());
        playableEntities.addAll(miners.values());
        return playableEntities;
    }

    public void addPlayableEntity(PlayableEntity playableEntity) {
        if (playableEntity instanceof Tank) {
            addTank((Tank) playableEntity);
        } else if (playableEntity instanceof Dropship) {
            addDropship((Dropship) playableEntity);
        } else if (playableEntity instanceof Miner) {
            addMiner((Miner) playableEntity);
        }
    }

    public PlayableEntity getPlayableEntity(Long entityId) {
        PlayableEntity playableEntity = getTank(entityId);
        if (playableEntity == null) {
            playableEntity = getDropship(entityId);
        }
        if (playableEntity == null) {
            playableEntity = getMiner(entityId);
        }
        return playableEntity;
    }

    public PlayableEntity getPlayableEntity(String ip) {
        PlayableEntity playableEntity = getTank(ip);
        if (playableEntity == null) {
            playableEntity = getDropship(ip);
        }
        if (playableEntity == null) {
            playableEntity = getMiner(ip);
        }
        return playableEntity;
    }

    public void removePlayableEntity(long entityId) {
        removeTank(entityId);
        removeDropship(entityId);
        removeMiner(entityId);
    }

    @Subscribe
    public void removePlayableEntityEvent(PlayableEntity playableEntity) {
        if (playableEntity instanceof Tank) {
            removeTankEvent((Tank) playableEntity);
        } else if (playableEntity instanceof Dropship) {
            removeDropshipEvent((Dropship) playableEntity);
        } else if (playableEntity instanceof Miner) {
            removeMinerEvent((Miner) playableEntity);
        }
    }

    // --------------------------------- Dropship ---------------------------------

    public ConcurrentMap<Long, Dropship> getDropships() {
        return dropships;
    }

    public void addDropship(Dropship dropship) {
        synchronized (dropships) {
            dropships.put(dropship.getId(), dropship);
            playersIP.put(dropship.getIp(), dropship.getId());
        }
        EventBus.getDefault().post(new SpawnEvent(dropship.getIntValue(), dropship.getPosition()));
    }

    public Dropship getDropship(Long dropshipId) {
        return dropships.get(dropshipId);
    }

    public Dropship getDropship(String ip){
        if (playersIP.containsKey(ip)){
            return dropships.get(playersIP.get(ip));
        }
        return null;
    }

    public void removeDropship(long dropshipId){
        synchronized (dropships) {
            Dropship dropship = dropships.remove(dropshipId);
            if (dropship != null) {
                playersIP.remove(dropship.getIp());
            }
        }
    }

    @Subscribe
    public void removeDropshipEvent(Dropship dropship){
        removeDropship(dropship.getId());
    }

    public void repairAllDropships() {
        for (Dropship dropship : dropships.values()) {
            dropship.repairUnits();
        }
    }

    // --------------------------------- Tank ---------------------------------

    public ConcurrentMap<Long, Tank> getTanks() {
        return tanks;
    }

    public void addTank(Tank tank) {
        synchronized (tanks) {
            tanks.put(tank.getId(), tank);
            playersIP.put(tank.getIp(), tank.getId());
        }
        EventBus.getDefault().post(new SpawnEvent(tank.getIntValue(), tank.getPosition()));
    }

    public Tank getTank(Long tankId) {
        return tanks.get(tankId);
    }

    public Tank getTank(String ip){
        if (playersIP.containsKey(ip)){
            return tanks.get(playersIP.get(ip));
        }
        return null;
    }

    public void removeTank(long tankId){
        synchronized (tanks) {
            Tank tank = tanks.remove(tankId);
            if (tank != null) {
                playersIP.remove(tank.getIp());
            }
        }
    }

    @Subscribe
    public void removeTankEvent(Tank tank){
        removeTank(tank.getId());
    }

    // --------------------------------- Miner ---------------------------------

    public ConcurrentMap<Long, Miner> getMiners() {
        return miners;
    }

    public void addMiner(Miner miner) {
        synchronized (miners) {
            miners.put(miner.getId(), miner);
            playersIP.put(miner.getIp(), miner.getId());
        }
        EventBus.getDefault().post(new SpawnEvent(miner.getIntValue(), miner.getPosition()));
    }

    public Miner getMiner(Long minerId) {
        return miners.get(minerId);
    }

    public Miner getMiner(String ip) {
        if (playersIP.containsKey(ip)) {
            return miners.get(playersIP.get(ip));
        }
        return null;
    }

    public void removeMiner(long minerId) {
        synchronized (miners) {
            Miner miner = miners.remove(minerId);
            if (miner != null) {
                playersIP.remove(miner.getIp());
            }
        }
    }

    @Subscribe
    public void removeMinerEvent(Miner miner){
        removeMiner(miner.getId());
    }
}
