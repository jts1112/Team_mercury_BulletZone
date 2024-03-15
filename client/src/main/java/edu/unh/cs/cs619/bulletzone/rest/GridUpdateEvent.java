package edu.unh.cs.cs619.bulletzone.rest;

import edu.unh.cs.cs619.bulletzone.util.GridWrapper;

public class GridUpdateEvent {
    public GridWrapper gw;

    public GridUpdateEvent(GridWrapper gw) {
        this.gw = gw;
    }
}
