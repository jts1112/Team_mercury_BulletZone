package edu.unh.cs.cs619.bulletzone.model;

import java.util.Optional;

public class ConcretePowerUpComponent implements PowerUpComponent {
    @Override
    public int getMovementInterval(int moveDelay) {
        return moveDelay;
    }

    @Override
    public int getFireInterval(int fireDelay) {
        return fireDelay;
    }
}
