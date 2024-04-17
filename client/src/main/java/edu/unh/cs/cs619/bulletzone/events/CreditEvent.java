package edu.unh.cs.cs619.bulletzone.events;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.unh.cs.cs619.bulletzone.R;

public class CreditEvent extends GameEvent {
    @JsonProperty
    private int balance;
    @JsonProperty
    private int creditDif;

    public CreditEvent() {}

    /**
     * Constructor for testing only.
     * @param balance The position of the entity to remove.
     * @param creditDif The raw server value of the entity.
     */
    public CreditEvent( int balance, int creditDif) {
        this.balance = balance;
        this.creditDif = creditDif;
    }

    /**
     * Public only for testing.
     * @param board The game board.
     */
    @Override
    public void applyTo(int[][] board, GameData gameData) {
        gameData.setPlayerCredits(balance);
    }

    @Override
    public String toString() {
        return "Added " + creditDif + " credits to a total of " + balance + super.toString();
    }
}
