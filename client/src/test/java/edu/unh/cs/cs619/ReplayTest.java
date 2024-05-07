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

import static org.junit.Assert.assertEquals;
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



    @Test
    public void testConstructor() {
        long expectedTimestampJoin = gameReplay.getTimestampJoin();
        long expectedTimestampLeave = gameReplay.getTimestampLeave();

        verify(mockDatabase).insert(eq("GameReplays"), eq(null), any());
    }

    @Test
    public void GameReplay_TakeSnapshot_IncreasesSnapshotAmount() {
        GridCell[][] gridData = new GridCell[16][16];


        gameReplay.takeSnapshot(gridData, mockDatabase, values);

        // Verify that a new snapshot is added to the snapshots list
        assertNotEquals("Snapshot list should not be empty", 0, gameReplay.getSnapshotSize());
    }

    @Test
    public void GameReplay_TakeSnapshot_CreatesCorrectSnapshot() {
        GridCell[][] gridData = new GridCell[16][16];


        long Id = gameReplay.getGameID();
        gameReplay.takeSnapshot(gridData, mockDatabase, values);


        // check that gamereplay id is 0 as the first replay
        assertEquals("Snapshot list should not be empty", 0, Id);
        assertNotEquals("Snapshot list should not be empty", 0, gameReplay.getSnapshotSize());
    }

}
