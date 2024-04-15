package edu.unh.cs.cs619.bulletzone.replay;

import java.util.ArrayList;

import edu.unh.cs.cs619.bulletzone.ui.GridCell;

public class GameReplay {
    private class Snapshot {
        long timestampJoin;
        long timestampLeave;
        GridCell[][] gridData;
    }


    ArrayList<Snapshot> snapshots = new ArrayList<>();
    int gameID;

    /**
     * Create a new GameReplay to put into the SQLite database
     */
    public GameReplay() {



    }

    /**
     * Retrieve a previously created GameReplay from the SQLite database
     * @param gameID ID of the game to retrieve
     */
    public GameReplay(long gameID) {

    }

    public void takeSnapshot() {

    }



}
