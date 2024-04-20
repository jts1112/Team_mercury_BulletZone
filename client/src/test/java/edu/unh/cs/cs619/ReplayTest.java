package edu.unh.cs.cs619;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

import edu.unh.cs.cs619.bulletzone.replay.GameReplay;
import edu.unh.cs.cs619.bulletzone.replay.GameReplayManager;
import edu.unh.cs.cs619.bulletzone.ui.GridCell;

public class ReplayTest {

    @Mock
    Context mockContext;

    @Mock
    SQLiteDatabase mockDatabase;
    @Mock
    Cursor mockCursor;
    GameReplayManager gameReplayManager;
    GameReplay gameReplay;
    ContentValues values;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        gameReplayManager = GameReplayManager.getInstance();
        values = mock(ContentValues.class);
        gameReplay = new GameReplay(mockDatabase, values);
    }

    /**
    @Test
    public void InitializeDatabase_GameReplayManagerInitialization_Success() {
        // Mock the Cursor object
        Cursor mockCursor = mock(Cursor.class);

        SQLiteOpenHelper dbHelper = mock(SQLiteOpenHelper.class);
        when(dbHelper.getWritableDatabase()).thenReturn(mockDatabase);

        // Mock the behavior of Cursor and JSONObject
        try {
            JSONObject mockJSONObject = mock(JSONObject.class);
            when(mockJSONObject.getJSONArray(anyString())).thenReturn(mock(JSONArray.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        when(mockDatabase.query(anyString(), any(), any(), any(), any(), any(), any()))
                .thenReturn(mockCursor);
        when(mockCursor.moveToFirst()).thenReturn(true);
        when(mockCursor.getInt(anyInt())).thenReturn(1); // Mocking game_id column
        when(mockCursor.getColumnIndex("game_id")).thenReturn(0); // Mocking column index

        // Call the method to be tested
        gameReplayManager.initializeDatabase(dbHelper);

        // Verify that the correct data is retrieved from the database
        verify(mockCursor, atLeastOnce()).getColumnIndex("game_id");
        verify(mockCursor, atLeastOnce()).getInt(0); // Assuming game_id is at index 0
        verify(mockCursor, atLeastOnce()).moveToFirst();
        verify(mockCursor, atLeastOnce()).close();
        verify(mockDatabase).close();
    }
     */

    @Test
    public void testConstructor() {
        long expectedTimestampJoin = gameReplay.getTimestampJoin();
        long expectedTimestampLeave = gameReplay.getTimestampLeave();

        verify(mockDatabase).insert(eq("GameReplays"), eq(null), any());
    }

    @Test
    public void testTakeSnapshot() {
        GridCell[][] gridData = new GridCell[16][16];


        gameReplay.takeSnapshot(gridData, mockDatabase, values);

        // Verify that a new snapshot is added to the snapshots list
        assertNotEquals("Snapshot list should not be empty", 0, gameReplay.getSnapshotSize());
    }

}
