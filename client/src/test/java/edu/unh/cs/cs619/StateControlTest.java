package edu.unh.cs.cs619;

import edu.unh.cs.cs619.bulletzone.ClientActivity;
import edu.unh.cs.cs619.bulletzone.R;
import edu.unh.cs.cs619.bulletzone.control.ControlUpdater;
import edu.unh.cs.cs619.bulletzone.control.*;
import edu.unh.cs.cs619.bulletzone.ui.GridCell;
import edu.unh.cs.cs619.bulletzone.ui.GridModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import android.view.View;
import android.widget.Button;

@RunWith(MockitoJUnitRunner.class)
public class StateControlTest {

    @Mock
    ClientActivity clientActivity;

    @Mock
    GridModel gridModel;

    @Mock
    Button buttonUp;
    @Mock
    Button buttonDown;
    @Mock
    Button buttonLeft;
    @Mock
    Button buttonRight;
    @Mock
    Button buttonFire;
    @Mock
    Button buttonEject;
    @Mock
    Button buttonMine;
    @Mock
    Button buttonTunnel;
    @Mock
    Button buttonEvadeLeft;
    @Mock
    Button buttonEvadeRight;

    ControlUpdater controlUpdater;

    @Before
    public void setUp() {
        // Mock the buttons
        when(clientActivity.findViewById(R.id.buttonUp)).thenReturn(buttonUp);
        when(clientActivity.findViewById(R.id.buttonDown)).thenReturn(buttonDown);
        when(clientActivity.findViewById(R.id.buttonLeft)).thenReturn(buttonLeft);
        when(clientActivity.findViewById(R.id.buttonRight)).thenReturn(buttonRight);
        when(clientActivity.findViewById(R.id.buttonFire)).thenReturn(buttonFire);
        when(clientActivity.findViewById(R.id.buttonEject)).thenReturn(buttonEject);
        when(clientActivity.findViewById(R.id.buttonMine)).thenReturn(buttonMine);
        when(clientActivity.findViewById(R.id.buttonTunnel)).thenReturn(buttonTunnel);
        when(clientActivity.findViewById(R.id.buttonEvadeLeft)).thenReturn(buttonEvadeLeft);
        when(clientActivity.findViewById(R.id.buttonEvadeRight)).thenReturn(buttonEvadeRight);

        controlUpdater = new ControlUpdater(clientActivity, gridModel);
    }

    @Test
    public void testTankControlState_UpdateControlsWithWalls_HidesAllButtons() {
        TankControlState tankControlState = new TankControlState(clientActivity);
        GridCell[] surroundingCells = new GridCell[5];
        for (int i = 0; i < surroundingCells.length; i++) {
            surroundingCells[i] = new GridCell(0,0,0,i,0);
        }

        // Test that all buttons are visible initially
        tankControlState.updateControls(surroundingCells);
        verify(buttonUp, times(1)).setVisibility(View.VISIBLE);
        verify(buttonDown, times(1)).setVisibility(View.VISIBLE);
        verify(buttonLeft, times(1)).setVisibility(View.VISIBLE);
        verify(buttonRight, times(1)).setVisibility(View.VISIBLE);
        verify(buttonFire, times(1)).setVisibility(View.VISIBLE);
        verify(buttonEject, times(1)).setVisibility(View.VISIBLE);
        verify(buttonMine, times(0)).setVisibility(View.VISIBLE);
        verify(buttonTunnel, times(0)).setVisibility(View.VISIBLE);
        verify(buttonEvadeLeft, times(1)).setVisibility(View.VISIBLE);
        verify(buttonEvadeRight, times(1)).setVisibility(View.VISIBLE);

        // Test that buttons are hidden when surrounded by obstacles
        surroundingCells[1].setEntityResourceID(R.drawable.wall5);
        surroundingCells[2].setEntityResourceID(R.drawable.wall5);
        surroundingCells[3].setEntityResourceID(R.drawable.wall5);
        surroundingCells[4].setEntityResourceID(R.drawable.wall5);
        tankControlState.updateControls(surroundingCells);
        verify(buttonUp, times(1)).setVisibility(View.GONE);
        verify(buttonDown, times(1)).setVisibility(View.GONE);
        verify(buttonLeft, times(1)).setVisibility(View.GONE);
        verify(buttonRight, times(1)).setVisibility(View.GONE);
        verify(buttonEvadeLeft, times(1)).setVisibility(View.GONE);
        verify(buttonEvadeRight, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void testTankControlState_UpdateTunnelButtonOverEntrance_RevealsTunnelButton() {
        TankControlState tankControlState = new TankControlState(clientActivity);
        GridCell[] surroundingCells = new GridCell[5];
        for (int i = 0; i < surroundingCells.length; i++) {
            surroundingCells[i] = new GridCell(0,0,0,i,0);
        }

        // Test that all buttons are visible initially
        tankControlState.updateControls(surroundingCells);
        verify(buttonUp, times(1)).setVisibility(View.VISIBLE);
        verify(buttonDown, times(1)).setVisibility(View.VISIBLE);
        verify(buttonLeft, times(1)).setVisibility(View.VISIBLE);
        verify(buttonRight, times(1)).setVisibility(View.VISIBLE);
        verify(buttonFire, times(1)).setVisibility(View.VISIBLE);
        verify(buttonEject, times(1)).setVisibility(View.VISIBLE);
        verify(buttonMine, times(0)).setVisibility(View.VISIBLE);
        verify(buttonTunnel, times(0)).setVisibility(View.VISIBLE);
        verify(buttonEvadeLeft, times(1)).setVisibility(View.VISIBLE);
        verify(buttonEvadeRight, times(1)).setVisibility(View.VISIBLE);

        // Test that tunnel button shows when over entrance terrain
        surroundingCells[0].setTerrainResourceID(R.drawable.entrance1);
        surroundingCells[1].setEntityResourceID(R.drawable.rock1);
        surroundingCells[2].setEntityResourceID(R.drawable.rock1);
        tankControlState.updateControls(surroundingCells);
        verify(buttonUp, times(1)).setVisibility(View.GONE);
        verify(buttonDown, times(1)).setVisibility(View.GONE);
        verify(buttonTunnel, times(1)).setVisibility(View.VISIBLE);
    }

    @Test
    public void testDropshipControlStateUpdateControls() {
        DropshipControlState dropshipControlState = new DropshipControlState(clientActivity);
        GridCell[] surroundingCells = new GridCell[5];
        for (int i = 0; i < surroundingCells.length; i++) {
            surroundingCells[i] = new GridCell(0,0,0,i,0);
        }

        // Test that all buttons are visible initially
        dropshipControlState.updateControls(surroundingCells);
        verify(buttonUp, times(1)).setVisibility(View.VISIBLE);
        verify(buttonDown, times(1)).setVisibility(View.VISIBLE);
        verify(buttonLeft, times(1)).setVisibility(View.VISIBLE);
        verify(buttonRight, times(1)).setVisibility(View.VISIBLE);
        verify(buttonFire, times(1)).setVisibility(View.VISIBLE);
        verify(buttonEject, times(1)).setVisibility(View.GONE);
        verify(buttonMine, times(1)).setVisibility(View.GONE);
        verify(buttonTunnel, times(1)).setVisibility(View.GONE);
        verify(buttonEvadeLeft, times(1)).setVisibility(View.GONE);
        verify(buttonEvadeRight, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void testMinerControlState_UpdateControlsWithWalls_HidesAllButtons() {
        MinerControlState minerControlState = new MinerControlState(clientActivity);
        GridCell[] surroundingCells = new GridCell[5];
        for (int i = 0; i < surroundingCells.length; i++) {
            surroundingCells[i] = new GridCell(0,0,0,i,0);
        }

        // Test that all buttons are visible initially
        minerControlState.updateControls(surroundingCells);
        verify(buttonUp, times(1)).setVisibility(View.VISIBLE);
        verify(buttonDown, times(1)).setVisibility(View.VISIBLE);
        verify(buttonLeft, times(1)).setVisibility(View.VISIBLE);
        verify(buttonRight, times(1)).setVisibility(View.VISIBLE);
        verify(buttonFire, times(1)).setVisibility(View.VISIBLE);
        verify(buttonEject, times(1)).setVisibility(View.VISIBLE);
        verify(buttonMine, times(1)).setVisibility(View.VISIBLE);
        verify(buttonTunnel, times(1)).setVisibility(View.VISIBLE);
        verify(buttonEvadeLeft, times(1)).setVisibility(View.VISIBLE);
        verify(buttonEvadeRight, times(1)).setVisibility(View.VISIBLE);

        // Test that buttons are hidden when surrounded by obstacles
        surroundingCells[1].setEntityResourceID(R.drawable.wall5);
        surroundingCells[2].setEntityResourceID(R.drawable.wall5);
        surroundingCells[3].setEntityResourceID(R.drawable.wall5);
        surroundingCells[4].setEntityResourceID(R.drawable.wall5);
        minerControlState.updateControls(surroundingCells);
        verify(buttonUp, times(1)).setVisibility(View.GONE);
        verify(buttonDown, times(1)).setVisibility(View.GONE);
        verify(buttonLeft, times(1)).setVisibility(View.GONE);
        verify(buttonRight, times(1)).setVisibility(View.GONE);
        verify(buttonEvadeLeft, times(1)).setVisibility(View.GONE);
        verify(buttonEvadeRight, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void testMinerControlState_UpdateControlsWithRocks_HidesSomeButtons() {
        MinerControlState minerControlState = new MinerControlState(clientActivity);
        GridCell[] surroundingCells = new GridCell[5];
        for (int i = 0; i < surroundingCells.length; i++) {
            surroundingCells[i] = new GridCell(0,0,0,i,0);
        }

        // Test that all buttons are visible initially
        minerControlState.updateControls(surroundingCells);
        verify(buttonUp, times(1)).setVisibility(View.VISIBLE);
        verify(buttonDown, times(1)).setVisibility(View.VISIBLE);
        verify(buttonLeft, times(1)).setVisibility(View.VISIBLE);
        verify(buttonRight, times(1)).setVisibility(View.VISIBLE);
        verify(buttonFire, times(1)).setVisibility(View.VISIBLE);
        verify(buttonEject, times(1)).setVisibility(View.VISIBLE);
        verify(buttonMine, times(1)).setVisibility(View.VISIBLE);
        verify(buttonTunnel, times(1)).setVisibility(View.VISIBLE);
        verify(buttonEvadeLeft, times(1)).setVisibility(View.VISIBLE);
        verify(buttonEvadeRight, times(1)).setVisibility(View.VISIBLE);

        // Test that some buttons are hidden and others aren't
        surroundingCells[1].setEntityResourceID(R.drawable.rock1);
        surroundingCells[2].setEntityResourceID(R.drawable.wall5);
        surroundingCells[3].setEntityResourceID(R.drawable.rock1);
        minerControlState.updateControls(surroundingCells);
        verify(buttonUp, times(1)).setVisibility(View.GONE);
        verify(buttonDown, times(1)).setVisibility(View.GONE);
        verify(buttonLeft, times(1)).setVisibility(View.GONE);
        verify(buttonEvadeLeft, times(1)).setVisibility(View.GONE);
    }
}
