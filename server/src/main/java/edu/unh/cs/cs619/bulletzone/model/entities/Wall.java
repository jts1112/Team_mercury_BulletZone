package edu.unh.cs.cs619.bulletzone.model.entities;

public class Wall extends FieldEntity {
    int destructValue, pos, life;

    public Wall() {
        // For indestructible wall
        this.destructValue = 1000;
        this.life = 10000;
    }

    public Wall(int destructValue, int pos) {
        // For destructible wall
        this.destructValue = destructValue;
        this.life = destructValue - 1000;
        this.pos = pos;
    }

    @Override
    public FieldEntity copy() {
        return new Wall();
    }

    @Override
    public int getIntValue() {
        return destructValue;
    }

    @Override
    public String toString() {
        return "W";
    }

    public int getPos() {
        return pos;
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
