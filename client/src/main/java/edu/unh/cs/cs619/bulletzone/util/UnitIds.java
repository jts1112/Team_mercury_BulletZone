package edu.unh.cs.cs619.bulletzone.util;

/**
 * Adapted into singleton pattern to be used as one copy of the
 * player ids for the client user
 */
public class UnitIds {

    private static UnitIds instance;

    private long tankId = -1;
    private long minerId = -1;
    private long dropshipId = -1;
    public long controlledUnitId = -1;

    private UnitIds() {

    }

    public static synchronized UnitIds getInstance() {
        if (instance == null) {
            instance = new UnitIds();
        }
        return instance;
    }

    public void setIds(long dropshipId, long minerId, long tankId) {
        this.dropshipId = dropshipId;
        this.minerId = minerId;
        this.tankId = tankId;
    }

    public long getTankId() {
        return tankId;
    }

    public void setControlledUnitId(long controlledUnitId) {
        this.controlledUnitId = controlledUnitId;
    }

    public long getControlledUnitId() {
        return controlledUnitId;
    }

    public long getMinerId() {
        return minerId;
    }

    public long getDropshipId() {
        return dropshipId;
    }
}

