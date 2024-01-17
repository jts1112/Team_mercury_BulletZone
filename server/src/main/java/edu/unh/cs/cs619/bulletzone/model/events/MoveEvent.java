package edu.unh.cs.cs619.bulletzone.model.events;

public class MoveEvent extends GameEvent{
    private int rawServerValue;
    private int oldPosition;
    private int newPosition;

    public MoveEvent(int rawServerValue, int oldPos, int newPos) {
        super(GameEventType.Move);
        this.rawServerValue = rawServerValue;
        this.oldPosition = oldPos;
        this.newPosition = newPos;
    }

}
