package edu.unh.cs.cs619.bulletzone.events;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReplayDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "replay_database.db";
    private static final int DATABASE_VERSION = 1;

    // Define the SQL statement to create the replay table
    private static final String SQL_CREATE_REPLAY_TABLE =
            "CREATE TABLE " + ReplayContract.ReplayEntry.TABLE_NAME + " (" +
                    ReplayContract.ReplayEntry._ID + " INTEGER PRIMARY KEY," +
                    // Define additional columns as needed
                    ");";

    public ReplayDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the replay table
        db.execSQL(SQL_CREATE_REPLAY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if needed
        // This method is called when the database version changes
    }
}

