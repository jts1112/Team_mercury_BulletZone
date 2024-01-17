package edu.unh.cs.cs619.bulletzone.model.events;

public class SpawnEvent extends GameEvent {
    private long rawServerValue;
    private int position;

    public SpawnEvent(int rawServerValue, int pos) {
        super(GameEventType.Spawn);
        this.rawServerValue = rawServerValue;
        this.position = pos;
    }
}
