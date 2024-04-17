package edu.unh.cs.cs619.bulletzone.model.events;

import com.fasterxml.jackson.annotation.JsonProperty;


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


    @Override
    public String toString() {
        return "Added " + creditDif + " credits to a total of " + balance + super.toString();
    }
}

