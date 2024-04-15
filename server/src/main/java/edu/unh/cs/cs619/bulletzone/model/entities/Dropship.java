package edu.unh.cs.cs619.bulletzone.model.entities;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.events.DamageEvent;

public class Dropship extends PlayableEntity {
    private static final int INITIAL_LIFE = 300;
    private static final int BULLET_DAMAGE = 50;
    private static final int FIRE_INTERVAL = 2000;
    private static final int ALLOWED_NUM_BULLETS = 1;
    private static final int MOVE_INTERVAL = 500;

    private int numMiners;
    private int numTanks;
    private long lastRepairTime;

    private List<Long> tankIds;
    private List<Long> minerIds;
    private List<Tank> dockedTanks;
    private List<Miner> dockedMiners;

    public Dropship(long id, Direction direction, String ip) {
        this.id = id;
        this.direction = direction;
        this.ip = ip;
        this.life = INITIAL_LIFE;
        this.numMiners = 1;
        this.numTanks = 1;
        this.numberOfBullets = 0;
        this.lastFireTime = 0;
        this.lastMoveTime = 0;
        this.allowedMoveInterval = MOVE_INTERVAL;
        this.allowedFireInterval = FIRE_INTERVAL;
        this.allowedNumberOfBullets = ALLOWED_NUM_BULLETS;
        this.bulletDamage = BULLET_DAMAGE;
        this.tankIds = new ArrayList<>();
        this.minerIds = new ArrayList<>();
        this.lastRepairTime = 0;
        this.dockedTanks = new ArrayList<>();
        this.dockedMiners = new ArrayList<>();
    }

    @Override
    public FieldEntity copy() {
        return new Dropship(id, direction, ip);
    }

    public boolean fire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFireTime >= FIRE_INTERVAL) {
            lastFireTime = currentTime;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "D";
    }

    public void addMiner(long minerId) {
        minerIds.add(minerId);
    }

    public void removeMiner(long minerId) {
        minerIds.remove(minerId);
    }

    public void addTank_(long tankId) {
        tankIds.add(tankId);
    }

    public void removeTank(long tankId) {
        tankIds.remove(tankId);
    }

    public void repairUnits() {
        long currentTime = System.currentTimeMillis();
        for (Miner miner : dockedMiners) {
            if (miner != null) {
                int repairedLife = Math.min(miner.getLife() + 2, 120);
                if (miner.getLife() != repairedLife) {
                    DamageEvent dmgEvent = new DamageEvent(miner.getPosition(), miner.getIntValue());
                    EventBus.getDefault().post(dmgEvent);
                }
                miner.setLife(repairedLife);
            }
            System.out.println("Miner " + miner.getId() + " health: " + miner.getLife());
        }
        for (Tank tank : dockedTanks) {
            if (tank != null) {
                int repairedLife = Math.min(tank.getLife() + 2, 100);
                if (tank.getLife() != repairedLife) {
                    DamageEvent dmgEvent = new DamageEvent(tank.getPosition(), tank.getIntValue());
                    EventBus.getDefault().post(dmgEvent);
                }
                tank.setLife(repairedLife);
            }
            System.out.println("Tank " + tank.getId() + " health: " + tank.getLife());
        }

        int repairedLife = Math.min(life + 1, INITIAL_LIFE);
        if (life != repairedLife) {
            DamageEvent dmgEvent = new DamageEvent(getPosition(), getIntValue());
            EventBus.getDefault().post(dmgEvent);
        }
        lastRepairTime = currentTime;
    }

    public int getNumMiners() {
        return numMiners;
    }

    public void setNumMiners(int numMiners) {
        this.numMiners = numMiners;
    }

    public int getNumTanks() {
        return numTanks;
    }

    public void setNumTanks(int numTanks) {
        this.numTanks = numTanks;
    }

    public List<Long> getMinerIds() {
        return minerIds;
    }

    public List<Long> getTankIds() {
        return tankIds;
    }

    @Override
    public int getIntValue() {
        return (int) (30_000_000 + (10_000 * id) + (10 * life) + Direction.toByte(direction));
    }

    // ----------------------- Docking ------------------------
    public void dockTank(Tank tank) {
        dockedTanks.add(tank);
    }

    public void undockTank(Tank tank) {
        dockedTanks.remove(tank);
    }

    public void dockMiner(Miner miner) {
        dockedMiners.add(miner);
    }

    public void undockMiner(Miner miner) {
        dockedMiners.remove(miner);
    }

    @Override
    public Boolean isWheeled() {
        return false;
    }

    @Override
    public Boolean isTracked() {
        return false;
    }
}
