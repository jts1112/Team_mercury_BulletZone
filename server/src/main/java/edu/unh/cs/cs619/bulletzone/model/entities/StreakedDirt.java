package edu.unh.cs.cs619.bulletzone.model.entities;

public class StreakedDirt extends Wall {

    public StreakedDirt(int position) {
        this.destructValue = 6000;
        this.life = 1;
        this.position = position;
    }

    @Override
    public FieldEntity copy() {
        return new StreakedDirt(position);
    }

    @Override
    public int getIntValue() {
        return destructValue;
    }

    @Override
    public String toString() {
        return "S";
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void hit(int damage) {
//        life = Math.max(life - damage, 0);
        life = life - damage;
        System.out.println("Wall life: " + life);
    }

    public int getLife() {
        return life;
    }
}
