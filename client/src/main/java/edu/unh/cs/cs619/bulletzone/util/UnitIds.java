package edu.unh.cs.cs619.bulletzone.util;

public class UnitIds {
    private long tankId;
    private long minerId;
    private long dropshipId;

    public UnitIds(long tankId, long minerId, long dropshipId) {
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
