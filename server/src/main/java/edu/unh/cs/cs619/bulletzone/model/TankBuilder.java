package edu.unh.cs.cs619.bulletzone.model;

public class TankBuilder {
    private int x;
    private int y;
    private Direction direction;
    private long id;
    private  String ip;
    private int life;
    FieldHolder parent;

    public TankBuilder setPosition(int x_coordinate, int y_coordinate) {
        this.x = x;
        this.y = y;
        return this;
    }

    public TankBuilder setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public TankBuilder setID(long id) {
        this.id = id;
        return  this;
    }
    public TankBuilder setIP(String ipadress){
        this.ip = ipadress;
        return this;
    }

    public TankBuilder setLife(int life) {
        this.life = life;
        return this;
    }

    public TankBuilder setParent(FieldHolder parent){
        this.parent = parent;
        return this;
    }
    public Tank build(){
        Tank tank = new Tank(this.id,this.direction,this.ip);
        tank.setParent(this.parent);
        return tank;
    }
}
