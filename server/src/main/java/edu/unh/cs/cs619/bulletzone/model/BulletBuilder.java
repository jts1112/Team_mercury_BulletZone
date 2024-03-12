package edu.unh.cs.cs619.bulletzone.model;

public class BulletBuilder {
    private long tankId;
    private Direction direction;
    private int bulletDamage;
    private int bulletID;
    private FieldHolder parent;

    public BulletBuilder setTankId(long tankId) {
        this.tankId = tankId;
        return  this;
    }

    public BulletBuilder setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public BulletBuilder setBulletDamage(int bulletDamage){
        this.bulletDamage = bulletDamage;
        return this;
    }

    public BulletBuilder setParent(FieldHolder parent) {
        this.parent = parent;
        return this;
    }

    public BulletBuilder setBulletID(int BulletID){
        this.bulletID = bulletID;
        return this;
    }

    /**
     * Bullet Builder build function that creates the bullet from the assigned parameters of the Bullet
     * @return A created bullet
     */
    public Bullet build(){
        // Create bullet class
        Bullet bullet = new Bullet(tankId,direction,bulletDamage);
        // Assign other parameters
        bullet.parent = parent;
        bullet.setBulletId(bulletID);
        return bullet;
    }
}
