package edu.unh.cs.cs619.bulletzone.events;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReplayDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "replay_database.db";
    private static final int DATABASE_VERSION = 1;

    // Define the SQL statement to create the replay table
    private static final String SQL_CREATE_REPLAY_TABLE =
            "CREATE TABLE GameReplays (" +
                    "game_id INTEGER PRIMARY KEY, " +
                    "timestamp_join LONG," +
                    "timestamp_leave LONG" +
                    ");";

    // Define the DQL statement to create the snapshot table
    private static final String SQL_CREATE_SNAPSHOT_TABLE =
            "CREATE TABLE Snapshots (" +
                    "id INTEGER PRIMARY KEY," +
                    "game_replay_id INTEGER," +
                    "grid_json TEXT," +
                    "timestamp LONG," +
                    "FOREIGN KEY (game_replay_id) REFERENCES GameReplays(id)" +
                    ");";

    public ReplayDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the replay table
        db.execSQL(SQL_CREATE_REPLAY_TABLE);
        db.execSQL(SQL_CREATE_SNAPSHOT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if needed
        // This method is called when the database version changes
    }
}

