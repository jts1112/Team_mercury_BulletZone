package edu.unh.cs.cs619.bulletzone.control;

import edu.unh.cs.cs619.bulletzone.ClientActivity;
import edu.unh.cs.cs619.bulletzone.ui.GridCell;

public interface ControlState {
    void updateControls(GridCell[] surroundingCells);
    void updateControlConditions(GridCell[] surroundingCells);
}

