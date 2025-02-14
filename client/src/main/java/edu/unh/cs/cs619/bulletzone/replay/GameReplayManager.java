package edu.unh.cs.cs619.bulletzone.replay;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Stack;

import edu.unh.cs.cs619.bulletzone.events.ReplayDBHelper;
import edu.unh.cs.cs619.bulletzone.ui.GridCell;

public class GameReplayManager {

    private static GameReplayManager instance;
    private static SQLiteDatabase db;
    //    private ArrayList<GameReplay> gameReplays = new ArrayList<>();
    private Stack<GameReplay> gameReplays;

    private GameReplayManager() {
        // Private constructor to prevent external instantiation
    }

    public static synchronized GameReplayManager getInstance(Context context) {
        if (instance == null) {
            instance = new GameReplayManager();
            instance.gameReplays = new Stack<>();
            instance.initializeDatabase(context);
        }
        return instance;
    }

    public static synchronized GameReplayManager getInstance() {
        if (instance == null) {
            instance = new GameReplayManager();
            instance.gameReplays = new Stack<>();
        }

        return instance;
    }

    public void initializeDatabase(Context context) {
        // Create or open the local SQLite database
        SQLiteOpenHelper dbHelper = new ReplayDBHelper(context);
        db = dbHelper.getWritableDatabase();

        // Retrieve data from the database
        Cursor cursor = db.query("GameReplays", null,
                null, null, null, null, null);

        // Process the retrieved data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract data from the cursor and create GameReplay objects
                int columnIndex = cursor.getColumnIndex("game_id");
                // Man I love Java!
                // This weird-ass implementation is a result of this error that should be a warning: https://stackoverflow.com/questions/71338033/android-sqlite-value-must-be-%E2%89%A5-0-getcolumnindex-kotlin?answertab=scoredesc
                if (columnIndex < 0) {
                    return;
                }
                int gameId = cursor.getInt(columnIndex);
                // Add more fields as needed based on your database schema
                GameReplay gameReplay = new GameReplay(gameId, db);
                gameReplays.add(gameReplay);
            } while (cursor.moveToNext());
            cursor.close();
        }

    }

    // for testing
    public void initializeDatabase(SQLiteOpenHelper dbHelper) {
        // Create or open the local SQLite database
        db = dbHelper.getWritableDatabase();

        // Retrieve data from the database
        Cursor cursor = db.query("GameReplays", null,
                null, null, null, null, null);

        // Process the retrieved data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract data from the cursor and create GameReplay objects
                int columnIndex = cursor.getColumnIndex("game_id");
                // Man I love Java!
                // This weird-ass implementation is a result of this error that should be a warning: https://stackoverflow.com/questions/71338033/android-sqlite-value-must-be-%E2%89%A5-0-getcolumnindex-kotlin?answertab=scoredesc
                if (columnIndex < 0) {
                    return;
                }
                int gameId = cursor.getInt(columnIndex);
                // Add more fields as needed based on your database schema
                GameReplay gameReplay = new GameReplay(gameId, db);
                gameReplays.add(gameReplay);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void startRecording() {
        this.gameReplays.push(new GameReplay(db));
    }

    public void endRecording() {
        this.gameReplays.peek().setTimestampLeave(System.currentTimeMillis());
    }

    public void takeSnapshot(GridCell[][] gridData) {
        this.gameReplays.peek().takeSnapshot(gridData, db);
    }

    public GameReplay getMostRecent() {
        return this.gameReplays.peek();
    }
}
