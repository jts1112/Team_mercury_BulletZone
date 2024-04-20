package edu.unh.cs.cs619.bulletzone.events;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.unh.cs.cs619.bulletzone.R;

public class CreditEvent extends GameEvent {
    @JsonProperty
    private int creditDif;

    public CreditEvent() {}

    /**
     * Constructor for testing only..
     * @param creditDif difference in credits.
     */
    public CreditEvent(int creditDif) {
        this.creditDif = creditDif;
    }

    /**
     * Public only for testing.
     * @param board The game board.
     */
    @Override
    public void applyTo(int[][] board) {
        GameData gameData = GameData.getInstance();
        gameData.addPlayerCredits(creditDif);
    }

    @Override
    public String toString() {
        return "Added " + creditDif + " credits to the bank " + super.toString();
    }
}
