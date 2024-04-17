package edu.unh.cs.cs619.bulletzone.events;

import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import edu.unh.cs.cs619.bulletzone.rest.GridUpdateEvent;

@EBean
public class GameEventProcessor {
    private int[][] board;
    private GameData gameData;

    public void setBoard(int[][] newBoard) { board = newBoard; }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    public void start() {
        EventBus.getDefault().register(this);
    }

    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onNewEvent(GameEvent event) {
        Log.d("GameEventProcessor", "Applying " + event);
        event.applyTo(board, gameData);
    }
}
