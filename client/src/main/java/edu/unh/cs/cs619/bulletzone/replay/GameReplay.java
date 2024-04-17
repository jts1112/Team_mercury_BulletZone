package edu.unh.cs.cs619.bulletzone.replay;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.JsonReader;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.unh.cs.cs619.bulletzone.events.ReplayDBHelper;
import edu.unh.cs.cs619.bulletzone.ui.GridCell;

public class GameReplay {
    private class Snapshot {
        long game_id;
        long timeStamp;
        GridCell[][] gridData;

        /**
         * Creating a new object to store in the database
         * @param game_id // ID of the current game being played
         * @param gridData // Grid currently being displayed
         */
        Snapshot(long game_id, GridCell[][] gridData, SQLiteDatabase db) {
            this.game_id = game_id;
            this.gridData = gridData;
            this.timeStamp = System.currentTimeMillis();

            ContentValues values = new ContentValues();

            values.put("timestamp_join", timestampJoin);
            values.put("timestamp_leave", timestampLeave);

            db.insert("GameReplays", null, values);
        }

        /**
         * Rebuilding object from the database
         * @param game_id game ID from game
         * @param timeStamp timestamp of snapshot
         * @param gridData grid at the timestamp stored as a JSON object
         */
        Snapshot(long game_id, long timeStamp, JSONObject gridData) {
            this.game_id = game_id;
            this.timeStamp = timeStamp;
            try {
                this.gridData = gridDataFromJSON(gridData);
            } catch (JSONException e) {
                this.gridData = new GridCell[16][16];
            }
        }

        private GridCell[][] gridDataFromJSON(JSONObject json) throws JSONException {
            GridCell[][] grid = new GridCell[16][16];
            JSONArray rowsArray = json.getJSONArray("gridData");
            for (int i = 0; i < rowsArray.length(); i++) {
                JSONArray columnsArray = rowsArray.getJSONArray(i);
                for (int j = 0; j < columnsArray.length(); j++) {
                    JSONObject cellJSON = columnsArray.getJSONObject(j);
                    grid[i][j] = new GridCell(cellJSON);
                }
            }
            return grid;
        }

        private JSONObject gridDataToJson(GridCell[][] grid) throws JSONException {
            JSONObject json = new JSONObject();
            JSONArray rowsArray = new JSONArray();
            for (int i = 0; i < 16; i++) {
                JSONArray columnsArray = new JSONArray();
                for (int j = 0; j < 16; j++) {
                    JSONObject cellJson = grid[i][j].toJson();
                    columnsArray.put(cellJson);
                }
                rowsArray.put(columnsArray);
            }
            json.put("gridData", rowsArray);
            return json;
        }

        // Populate toJson method
        private JSONObject toJson() throws JSONException {
            JSONObject json = new JSONObject();
            json.put("timeStamp", timeStamp);
            json.put("gridData", gridDataToJson(gridData));
            return json;
        }

        private void saveToDB(SQLiteDatabase db) {
            ContentValues values = new ContentValues();

            values.put("timestamp_join", timestampJoin);
            values.put("timestamp_leave", timestampLeave);


            db.insert("GameReplays", null, values);
        }
    }


    ArrayList<Snapshot> snapshots = new ArrayList<>();
    long gameID;
    long timestampJoin;
    long timestampLeave;

    /**
     * Create a new GameReplay to put into the SQLite database
     */
    public GameReplay(SQLiteDatabase db) {
        this.timestampJoin = System.currentTimeMillis();
        this.timestampLeave = -1;

        ContentValues values = new ContentValues();
        values.put("timestamp_join", timestampJoin);
        values.put("timestamp_leave", timestampLeave);

        this.gameID = db.insert("GameReplays", null, values);
    }

    /**
     * Retrieve a previously created GameReplay from the SQLite database
     * @param gameID ID of the game to retrieve
     * @param db instance of the local SQLiteDatabase
     */
    @SuppressLint("Range")
    public GameReplay(int gameID, SQLiteDatabase db) {
        // Populate gameID, timestampJoin, timestampLeave
        this.gameID = gameID;

        Cursor replayCursor = db.query(
                "GameReplays",
                null,
                "id=?",
                new String[] {String.valueOf(gameID)},
                null,
                null,
                null
        );

        this.timestampJoin = replayCursor.getLong(replayCursor.getColumnIndex("timestamp_join"));
        this.timestampLeave = replayCursor.getLong(replayCursor.getColumnIndex("timestamp_leave"));

        replayCursor.close();

        Cursor snapshotCursor = db.query(
                "Snapshots",
                null,
                "game_id=?",
                new String[] {String.valueOf(gameID)},
                null,
                null,
                "timestamp"
        );

        // Process retrieved data
        if (snapshotCursor != null && snapshotCursor.moveToFirst()) {
            do {
                long timestamp = snapshotCursor.getLong(snapshotCursor.getColumnIndex("timestamp"));
                String jsonData = snapshotCursor.getString(snapshotCursor.getColumnIndex("grid_json"));
                Snapshot ss;

                try {
                    ss = new Snapshot(gameID, timestamp, new JSONObject(jsonData));
                } catch (JSONException e) {
                    Log.d("GameReplay", "Error creating snapshot from the database.");
                    ss = null;
                }

                this.snapshots.add(ss);

            } while (snapshotCursor.moveToNext());
            snapshotCursor.close();
        }
    }

    public long getGameID() {
        return gameID;
    }

    /**
     * Setter for the leaveMatch timestamp
     * @param timestampLeave when the player left the match
     */
    public void setTimestampLeave(long timestampLeave) {
        this.timestampLeave = timestampLeave;
    }

    /**
     * Take a snapshot of the board and store it in the database
     * @param gridData Grid data to store in the database
     * @param db Database to store data in.
     */
    public void takeSnapshot(GridCell[][] gridData, SQLiteDatabase db) {
        this.snapshots.add(new Snapshot(gameID, gridData, db));
    }
}
