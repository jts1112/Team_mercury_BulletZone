package edu.unh.cs.cs619.bulletzone.events;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SpawnEvent extends GameEvent {
    @JsonProperty
    private int rawServerValue;
    @JsonProperty
    private int position;

    public SpawnEvent() {}

    void applyTo(int [][]board) {
        board[position / 16][position % 16] = rawServerValue;
    }

    @Override
    public String toString() {
        return "Spawn " + rawServerValue +
                " at " + position +
                super.toString();
    }
}
