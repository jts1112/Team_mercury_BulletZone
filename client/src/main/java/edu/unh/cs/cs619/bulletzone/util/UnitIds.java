package edu.unh.cs.cs619.bulletzone.util;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Adapted into singleton pattern to be used as one copy of the
 * player ids for the client user
 */
public class UnitIds {

    private static UnitIds instance;

    private Queue<Long> tankIds = new LinkedList<>();
    private Queue<Long> minerIds = new LinkedList<>();

    private long dropshipId = -1;
    public long controlledUnitId = -1;

    private UnitIds() {}

    // -------- Getters --------

    public static synchronized UnitIds getInstance() {
        if (instance == null) {
            instance = new UnitIds();
        }
        return instance;
    }

    public long getDropshipId() {
        return dropshipId;
    }

    public long getTankId() {
        if (tankIds.isEmpty()) {
            return -1;
        }
        Long tankId = tankIds.remove();
        addTankId(tankId);
        return tankId;
    }

    public long getMinerId() {
        if (minerIds.isEmpty()) {
            return -1;
        }
        Long minerId = minerIds.remove();
        addMinerId(minerId);
        return minerId;
    }

    public long getControlledUnitId() {
        return controlledUnitId;
    }

    // -------- Setters --------

    public void setIds(long dropshipId, long minerId, long tankId) {
        this.dropshipId = dropshipId;
        addMinerId(minerId);
        addTankId(tankId);
    }

    public void setControlledUnitId(long controlledUnitId) {
        this.controlledUnitId = controlledUnitId;
    }

    public void setDropshipId(long dropshipId) {
        this.dropshipId = dropshipId;
    }

    public void addTankId(long tankId) {
        tankIds.add(tankId);
    }

    public void addMinerId(long minerId) {
        minerIds.add(minerId);
    }
}
