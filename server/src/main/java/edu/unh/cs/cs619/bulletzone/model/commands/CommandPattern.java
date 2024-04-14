package edu.unh.cs.cs619.bulletzone.model.commands;


import java.util.ArrayList;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;

public class CommandPattern {

    private ArrayList<Command> commandstoExecute; // Array list containing commands
    private Tank tank;

    //TODO TurnCommand turnCommand;
    //TODO MoveCommand moveCommand;
    // TODO FireCommand fireCommand;

    public CommandPattern(Tank tank1){
        this.commandstoExecute = new ArrayList<Command>();
        this.tank = tank1;
    }
    public void addTurnCommand(long tankId, Direction direction){
        commandstoExecute.add(new TurnCommand(tankId, direction));
    }
    public  void addFireCommand(long tankId, int bulletType){
        commandstoExecute.add(new FireCommand(tankId, bulletType));
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
     * Execute Method for CommandPattern to execute Queued Commands
     * @return returns True if all sucess and False if even one failure
     * @throws TankDoesNotExistException
     * TODO add game as parameter to execute command method to make simpler.
     * makes it so no need to pass game in every time.
     */
    public Boolean executeCommands() throws TankDoesNotExistException {
        for (Command currentCommand: commandstoExecute) {
            if(currentCommand.execute(tank) == Boolean.FALSE) {
                return false;
            }
        }
        commandstoExecute.clear();
        return Boolean.TRUE;
    }
}
