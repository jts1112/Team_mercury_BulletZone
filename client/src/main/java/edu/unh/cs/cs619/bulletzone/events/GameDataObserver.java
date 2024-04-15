package edu.unh.cs.cs619.bulletzone.events;

public interface GameDataObserver {
    void onTankLifeUpdate(long tankLife);

    void onMinerLifeUpdate(long minerLife);

    void onDropshipLifeUpdate(long dropshipLife);
}
