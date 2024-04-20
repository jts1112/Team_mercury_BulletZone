package edu.unh.cs.cs619.bulletzone.model.commands;

import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;

public interface Command {
    Boolean execute(PlayableEntity entity) throws TankDoesNotExistException;
}
