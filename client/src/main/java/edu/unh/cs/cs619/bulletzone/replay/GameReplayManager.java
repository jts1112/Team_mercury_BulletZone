package edu.unh.cs.cs619.bulletzone.replay;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import edu.unh.cs.cs619.bulletzone.events.ReplayDBHelper;

public class GameReplayManager {

    private static GameReplayManager instance;
    private ArrayList<GameReplay> gameReplays = new ArrayList<>();

    private GameReplayManager() {
        // Private constructor to prevent external instantiation
    }

    public static synchronized GameReplayManager getInstance(Context context) {
        if (instance == null) {
            instance = new GameReplayManager();
            instance.initializeDatabase(context);
        }
        return instance;
    }

    private void initializeDatabase(Context context) {
        // Create or open the local SQLite database
        SQLiteOpenHelper dbHelper = new ReplayDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Retrieve data from the database
        Cursor cursor = db.query(ReplayContract.ReplayEntry.TABLE_NAME, null,
                null, null, null, null, null);

        // Process the retrieved data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract data from the cursor and create GameReplay objects
                long gameId = cursor.getLong(cursor.getColumnIndex(ReplayContract.ReplayEntry._ID));
                // Add more fields as needed based on your database schema
                GameReplay gameReplay = new GameReplay(gameId);
                gameReplays.add(gameReplay);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Close the database connection
        db.close();
    }

    public void startRecording() {

    }

    public void endRecording() {

    }

    public GameReplay getMostRecent() {
        return null;
    }
}
