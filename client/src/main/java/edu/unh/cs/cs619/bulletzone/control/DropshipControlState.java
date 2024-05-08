package edu.unh.cs.cs619.bulletzone.control;

import android.view.View;
import android.widget.Button;

import edu.unh.cs.cs619.bulletzone.ClientActivity;
import edu.unh.cs.cs619.bulletzone.R;
import edu.unh.cs.cs619.bulletzone.ui.GridCell;

public class DropshipControlState implements ControlState {
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

    public DropshipControlState(ClientActivity activity) {
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
        // Show all buttons
        buttonUp.setVisibility(View.VISIBLE);
        buttonDown.setVisibility(View.VISIBLE);
        buttonLeft.setVisibility(View.VISIBLE);
        buttonRight.setVisibility(View.VISIBLE);
        buttonFire.setVisibility(View.VISIBLE);
        buttonEject.setVisibility(View.GONE);
        buttonMine.setVisibility(View.GONE);
        buttonTunnel.setVisibility(View.GONE);
        buttonEvadeLeft.setVisibility(View.GONE);
        buttonEvadeRight.setVisibility(View.GONE);

        // Update text values for move buttons
        buttonLeft.setText("Turn Left");
        buttonRight.setText("Turn Right");
        buttonUp.setText("Turn Up");
        buttonDown.setText("Turn Down");
    }

    @Override
    public void updateControlConditions(GridCell[] surroundingCells) {
    }
}
