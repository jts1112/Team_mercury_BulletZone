package edu.unh.cs.cs619.bulletzone.model.entities;

public class DirtRock extends FieldEntity {
    int destructValue, pos, life;

    public DirtRock() {
        // For indestructible rock
        this.destructValue = 5000;
    }

    public DirtRock(int destructValue, int pos) {
        // For destructible dirt
        this.destructValue = destructValue;
        this.life = destructValue - 5000;
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
        return "R";
    }

    public int getPos() {
        return pos;
    }

    @Override
    public void hit(int damage) {
        life = life - damage;
        System.out.println("Rock life: " + life);
    }

    public int getLife() {
        return life;
    }
}
