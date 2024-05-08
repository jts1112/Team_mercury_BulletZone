package edu.unh.cs.cs619.bulletzone.util;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


/**
 * Adapted into singleton pattern to be used as one copy of the
 * player ids for the client user
 */
public class UnitIds {

    private static UnitIds instance;

    private Queue<Long> tankIds = new LinkedList<>();
    private Queue<Long> minerIds = new LinkedList<>();

    private Set<Long> tankIdSet = new LinkedHashSet<>();
    private Set<Long> minerIdSet = new LinkedHashSet<>();

    public Map<Long, Integer[]> tankImageResources = new HashMap<>();
    public Map<Long, Integer[]> minerImageResources = new HashMap<>();

    private long dropshipId = -1;

    public long controlledUnitId = -1;

    private UnitIds() {}

    // ---------------- Getters ----------------
    public static synchronized UnitIds getInstance() {
        if (instance == null) {
            instance = new UnitIds();
        }
        return instance;
    }

    public long getDropshipId() {
        return dropshipId;
    }

    public long getNextTankId() {
        if (tankIds.isEmpty()) {
            return -1;
        }
        Long tankId = tankIds.remove();
        tankIds.add(tankId);
        return tankId;
    }

    public long getNextMinerId() {
        if (minerIds.isEmpty()) {
            return -1;
        }
        Long minerId = minerIds.remove();
        minerIds.add(minerId);
        return minerId;
    }

    public long getControlledUnitId() {
        return controlledUnitId;
    }

    public Queue<Long> getTankIdQueue() {
        return tankIds;
    }
    public Queue<Long> getMinerIdQueue() {
        return minerIds;
    }

    public Set<Long> getTankIdSet() {
        return tankIdSet;
    }

    public Set<Long> getMinerIdSet() {
        return minerIdSet;
    }

    public Long getFirstTankId() {
        return tankIdSet.isEmpty() ? null : tankIdSet.iterator().next();
    }

    public Long getFirstMinerId() {
        return minerIdSet.isEmpty() ? null : minerIdSet.iterator().next();
    }

    public Integer[] getTankImageResource(long tankId) {
        return tankImageResources.get(tankId);
    }

    public Integer[] getMinerImageResource(long minerId) {
        return minerImageResources.get(minerId);
    }

    // ---------------- Setters ----------------

    public void setIds(long dropshipId) {
        this.dropshipId = dropshipId;
    }

    public void setControlledUnitId(long controlledUnitId) {
        this.controlledUnitId = controlledUnitId;
    }

    public void addTankId(long tankId, Integer[] imageResources) {
        if (tankIdSet.add(tankId)) {
            tankIds.add(tankId);
            tankImageResources.put(tankId, imageResources);
        }
    }

    public void addMinerId(long minerId, Integer[] imageResources) {
        if (minerIdSet.add(minerId)) {
            minerIds.add(minerId);
            minerImageResources.put(minerId, imageResources);
        }
    }
}
