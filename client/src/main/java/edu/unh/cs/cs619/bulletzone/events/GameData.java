package edu.unh.cs.cs619.bulletzone.events;

import java.util.ArrayList;
import java.util.List;

import edu.unh.cs.cs619.bulletzone.util.UnitIds;

/**
 * GameData for client to know basic player info at runtime
 * and update UI with observer pattern
 */
public class GameData {
    private static GameData instance;
    private UnitIds unitIds;
    private List<GameDataObserver> observers = new ArrayList<>();

    // Life values
    private long dropshipLife;
    private long minerLife;
    private long tankLife;

    // Movement and fire rates
    private int dropshipMovementRate;
    private int minerMovementRate;
    private int tankMovementRate;

    private int dropshipFireRate;
    private int minerFireRate;
    private int tankFireRate;

    // Player credits
    private long playerCredits;

    // Constructor
    public GameData() {
        this.unitIds = UnitIds.getInstance();
    }

    // Method to get the singleton instance
    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    public void registerObserver(GameDataObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(GameDataObserver observer) {
        observers.remove(observer);
    }

    public long getDropshipLife() {
        return dropshipLife;
    }

    public void setDropshipLife(long dropshipVal) {
        if ((dropshipVal - 30000000) / 10000 == unitIds.getDropshipId()) {
            this.dropshipLife = (dropshipVal - (30000000 + (unitIds.getDropshipId() * 10000))) / 10;
            notifyDropshipLifeObservers();
        }
    }

    public long getMinerLife() {
        return minerLife;
    }

    public void setMinerLife(long minerVal) {
        if ((minerVal - 20000000) / 10000 == unitIds.getFirstMinerId()) {
            this.minerLife = (minerVal - (20000000 + (unitIds.getFirstMinerId() * 10000))) / 10;
            notifyMinerLifeObservers();
        }
    }

    public long getTankLife() {
        return tankLife;
    }

    public void setTankLife(long tankVal) {
        if ((tankVal - 10000000) / 10000 == unitIds.getFirstTankId()) {
            this.tankLife = (tankVal - (10000000 + (unitIds.getFirstTankId() * 10000))) / 10;
            notifyTankLifeObservers();
        }
    }

    public void setPlayerCredits(long creditVal) {
        this.playerCredits = creditVal;
//        Log.d("GameData", "Credits set to " + playerCredits);
    }

    public void addPlayerCredits(long creditDif) {
        this.playerCredits += creditDif;
//        Log.d("GameData", "Credits now at " + playerCredits);
        notifyCreditObservers();
    }

    public long getPlayerCredits() {
        return playerCredits;
    }

    private void notifyTankLifeObservers() {
        for (GameDataObserver observer : observers) {
            observer.onTankLifeUpdate(tankLife);
        }
    }

    private void notifyMinerLifeObservers() {
        for (GameDataObserver observer : observers) {
            observer.onMinerLifeUpdate(minerLife);
        }
    }

    private void notifyDropshipLifeObservers() {
        for (GameDataObserver observer : observers) {
            observer.onDropshipLifeUpdate(dropshipLife);
        }
    }

    private void notifyCreditObservers() {
        for (GameDataObserver observer : observers) {
            observer.onPlayerCreditUpdate(playerCredits);
        }
    }
}
