package edu.unh.cs.cs619.bulletzone.model;

public interface Command {
    Boolean execute(Game game) throws TankDoesNotExistException;
}
