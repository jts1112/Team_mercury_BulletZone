package edu.unh.cs.cs619.bulletzone.model.powerUps;

/**
 * a tuple (as for some reason java doesn't have them by default)
 *
 * @param <Integer>> damage
 * @param <Boolean>> if the damage has been shielded
 */
public class DamageTuple<Integer, Boolean> {
    public int damage;
    public final boolean shielded;

    /**
     *
     * @param damage damage dealt
     * @param shielded if the damage has been shielded
     */
    public DamageTuple(int damage, boolean shielded) {
        this.damage = damage;
        this.shielded = shielded;
    }
}
