package edu.unh.cs.cs619.bulletzone.control;

import android.view.View;
import android.widget.Button;

import edu.unh.cs.cs619.bulletzone.ClientActivity;
import edu.unh.cs.cs619.bulletzone.R;
import edu.unh.cs.cs619.bulletzone.ui.GridCell;

public class TankControlState implements ControlState {

    private Button buttonDown;
    private Button buttonUp;
    private Button buttonLeft;
    private Button buttonEvadeLeft;
    private Button buttonRight;
    private Button buttonEvadeRight;
    private Button buttonFire;
    private Button buttonEject;
    private Button buttonMine;
    private Button buttonTunnel;

    public TankControlState(ClientActivity activity) {
        buttonDown = activity.findViewById(R.id.buttonDown);
        buttonUp = activity.findViewById(R.id.buttonUp);
        buttonLeft = activity.findViewById(R.id.buttonLeft);
        buttonEvadeLeft = activity.findViewById(R.id.buttonEvadeLeft);
        buttonRight = activity.findViewById(R.id.buttonRight);
        buttonEvadeRight = activity.findViewById(R.id.buttonEvadeRight);
        buttonFire = activity.findViewById(R.id.buttonFire);
        buttonEject = activity.findViewById(R.id.buttonEject);
        buttonMine = activity.findViewById(R.id.buttonMine);
        buttonTunnel = activity.findViewById(R.id.buttonTunnel);
    }

    @Override
    public void updateControls(GridCell[] surroundingCells) {
        // Logic for updating controls for default tank unit
        buttonUp.setVisibility(View.VISIBLE);
        buttonDown.setVisibility(View.VISIBLE);
        buttonLeft.setVisibility(View.VISIBLE);
        buttonRight.setVisibility(View.VISIBLE);
        buttonFire.setVisibility(View.VISIBLE);
        buttonEject.setVisibility(View.VISIBLE);
        buttonMine.setVisibility(View.GONE);
        buttonTunnel.setVisibility(View.GONE);
        buttonEvadeLeft.setVisibility(View.VISIBLE);
        buttonEvadeRight.setVisibility(View.VISIBLE);

        // Update text values for move buttons
        buttonLeft.setText("LEFT");
        buttonRight.setText("RIGHT");
        buttonUp.setText("UP");
        buttonDown.setText("DOWN");

        if (surroundingCells != null)
            updateControlConditions(surroundingCells);
    }

    @Override
    public void updateControlConditions(GridCell[] surroundingCells) {
        int wall = R.drawable.wall5;
        int rock = R.drawable.rock1;

        if (surroundingCells[0].getTerrainResourceID() == R.drawable.entrance1) {
            buttonTunnel.setVisibility(View.VISIBLE);
        }
        if (surroundingCells[1].getEntityResourceID() == wall || surroundingCells[1].getEntityResourceID() == rock) {
            buttonUp.setVisibility(View.GONE);
        }
        if (surroundingCells[2].getEntityResourceID() == wall || surroundingCells[2].getEntityResourceID() == rock) {
            buttonDown.setVisibility(View.GONE);
        }
        if (surroundingCells[3].getEntityResourceID() == wall || surroundingCells[3].getEntityResourceID() == rock) {
            buttonLeft.setVisibility(View.GONE);
            buttonEvadeLeft.setVisibility(View.GONE);
        }
        if (surroundingCells[4].getEntityResourceID() == wall || surroundingCells[4].getEntityResourceID() == rock) {
            buttonRight.setVisibility(View.GONE);
            buttonEvadeRight.setVisibility(View.GONE);
        }
    }
}