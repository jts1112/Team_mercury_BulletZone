package edu.unh.cs.cs619.bulletzone.model.events;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CreditEvent extends GameEvent {
    @JsonProperty
    private int creditDif;

    public CreditEvent() {}

    /**
     * Constructor for testing only.
     * @param creditDif value of credits added/subtracted
     */
    public CreditEvent(int creditDif) {
        this.creditDif = creditDif;
    }


    @Override
    public String toString() {
        return "Added " + creditDif + " credits to the bank " + super.toString();
    }
}

