package edu.unh.cs.cs619.bulletzone.model.entities;

public class Wall extends FieldEntity {
    protected int destructValue, position, life;

    public Wall() {
        // For indestructible wall
        this.destructValue = 1000;
        this.life = 10000;
    }

    public Wall(int destructValue, int position) {
        // For destructible wall
        this.destructValue = destructValue;
        this.life = destructValue - 1000;
        this.position = position;
    }

    @Override
    public FieldEntity copy() {
        return new Wall();
    }

    @Override
    public int getIntValue() {
//        return destructValue;
        if (destructValue == 1000) { // check if invincible wall
            return destructValue;
        } else {
            return 1000 + life;
        }
    }

    @Override
    public String toString() {
        return "W";
    }

    public int getPos() {
        return position;
    }

    @Override
    public void hit(int damage) {
        life = life - damage;
        System.out.println("Wall life: " + life);
    }

    public int getLife() {
        return life;
    }
}
