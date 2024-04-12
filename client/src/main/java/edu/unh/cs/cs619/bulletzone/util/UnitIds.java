package edu.unh.cs.cs619.bulletzone.util;

import java.io.Serializable;

public class UnitIds {
    private long tankId = -1;
    private long minerId = -1;
    private long dropshipId = -1;

    public UnitIds(long dropshipId, long minerId, long tankId) {
        this.tankId = tankId;
        this.minerId = minerId;
        this.dropshipId = dropshipId;
    }

    public long getTankId() {
        return tankId;
    }

    public long getMinerId() {
        return minerId;
    }

    public long getDropshipId() {
        return dropshipId;
    }
}
