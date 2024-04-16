package edu.unh.cs.cs619.bulletzone.model.commands;


import java.util.ArrayList;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;

public class CommandPattern {

    private ArrayList<Command> commandstoExecute; // Array list containing commands
    private PlayableEntity currentEntity;
    private final Object monitor = new Object();

    //TODO TurnCommand turnCommand;
    //TODO MoveCommand moveCommand;
    // TODO FireCommand fireCommand;

    public CommandPattern(PlayableEntity currentEntity){
        this.commandstoExecute = new ArrayList<Command>();
        this.currentEntity = currentEntity;
    }
    public void addTurnCommand(long tankId, Direction direction){
        commandstoExecute.add(new TurnCommand(tankId, direction));
    }
    public  void addFireCommand(long tankId, int bulletType) {
        synchronized (this.monitor) {
            commandstoExecute.add(new FireCommand(tankId, bulletType, this.monitor));
        }
    }

    /**
     * Adds a MoveCommand to the CommandPattern stack to be executed later
     * @param tankId passed in tank ID
     * @param direction the direction object being passed in.
     */
    public void addMoveCommand(long tankId, Direction direction){
        commandstoExecute.add(new MoveCommand(tankId, direction));
    }

    /**
     * Checks whether their are comands waiting to be executed or not.
     * used in Action controller when queuing up commands
     * @return True if their are commands to be executed. else False.
     */
    public Boolean isEmpty(){
        return commandstoExecute.isEmpty();
    }

    /**
     * Execute Method for CommandPattern to execute Queued Commands
     * @return returns True if all sucess and False if even one failure
     * @throws TankDoesNotExistException
     * TODO add game as parameter to execute command method to make simpler.
     * makes it so no need to pass game in every time.
     */
    public Boolean executeCommands() throws TankDoesNotExistException {
        for (Command currentCommand: commandstoExecute) {
            if(currentCommand.execute(currentEntity) == Boolean.FALSE) {
                return false;
            } else { // command was successfuly execued and cann remove from front of the list.
                commandstoExecute.remove(0);
            }
        }
        return Boolean.TRUE;
    }
}
