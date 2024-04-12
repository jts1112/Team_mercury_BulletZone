package edu.unh.cs.cs619.bulletzone.model;

public interface Command {
    Boolean execute(Tank tank1) throws TankDoesNotExistException;
}
