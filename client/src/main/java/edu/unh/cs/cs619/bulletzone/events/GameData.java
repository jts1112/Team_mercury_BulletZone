package edu.unh.cs.cs619.bulletzone.events;

import java.util.ArrayList;
import java.util.List;

import edu.unh.cs.cs619.bulletzone.util.UnitIds;

public class GameData {
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
    private int playerCredits;

    // Constructor
    public GameData(UnitIds unitIds) {
        this.unitIds = unitIds;
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
        if ((minerVal - 20000000) / 10000 == unitIds.getMinerId()) {
            this.minerLife = (minerVal - (20000000 + (unitIds.getMinerId() * 10000))) / 10;
            notifyMinerLifeObservers();
        }
    }

    public long getTankLife() {
        return tankLife;
    }

    public void setTankLife(long tankVal) {
        if ((tankVal - 10000000) / 10000 == unitIds.getTankId()) {
            this.tankLife = (tankVal - (10000000 + (unitIds.getTankId() * 10000))) / 10;
            notifyTankLifeObservers();
        }
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
}
