package edu.unh.cs.cs619.bulletzone.events;

import android.icu.text.Transliterator;

import com.fasterxml.jackson.annotation.JsonProperty;
public class RemovalEvent extends GameEvent {
    @JsonProperty
    private long entityId;

    public RemovalEvent() {}

    @Override
    void applyTo(int[][] board) {
        Transliterator.Position pos = findPositionById(entityId);
        if (pos != null) {
            board[pos.x][pos.y] = 0; /
        }
    }

    public RemovalEvent(long entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        return "Remove entity " + entityId + super.toString();
    }
}
