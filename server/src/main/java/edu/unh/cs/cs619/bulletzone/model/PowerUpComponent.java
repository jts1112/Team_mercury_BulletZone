package edu.unh.cs.cs619.bulletzone.model;

public interface PowerUpComponent {
    int getMovementInterval( int moveDelay);

    int getFireInterval(int fireDelay);
}
