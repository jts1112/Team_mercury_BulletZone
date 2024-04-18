package edu.unh.cs.cs619.bulletzone.model.powerUps;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PowerUpComponentTest {
    static Game game = new Game();
    Tank tank;

    @Before
    public void setup() {
        // Build empty board for testing
        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16,new Object()).build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2);

        tank = new Tank(1, Direction.Up,"127.0.0.1");

        fieldElement.setFieldEntity(tank);
        tank.setParent(fieldElement);

        game.addTank(tank);

        tank.setDirection(Direction.Down);
    }

    @After
    public void tearDown() {
        game.removeTank(1);
    }

    // Individual Tests

    // AntiGrav
    @Test
    public void getAllowedMoveInterval_AntiGrav_250() {
        tank.pickupPowerUp(PowerUpType.AntiGrav);
        assertEquals(250, tank.getAllowedMoveInterval());
    }

    @Test
    public void getAllowedFireInterval_AntiGrav_510() {
        tank.pickupPowerUp(PowerUpType.AntiGrav);
        assertEquals(600, tank.getAllowedFireInterval());
    }

    // Fusion Reactor
    @Test
    public void getAllowedMoveInterval_FusionReactor_510() {
        tank.pickupPowerUp(PowerUpType.FusionReactor);
        assertEquals(600, tank.getAllowedMoveInterval());
    }

    @Test
    public void getAllowedFireInterval_FusionReactor_250() {
        tank.pickupPowerUp(PowerUpType.FusionReactor);
        assertEquals(250, tank.getAllowedFireInterval());
    }

    // Stacking Tests

    // AntiGrav then Fusion Reactor
    @Test
    public void getAllowedMoveInterval_AntiGravThenFusionReactor_350() {
        tank.pickupPowerUp(PowerUpType.AntiGrav);
        tank.pickupPowerUp(PowerUpType.FusionReactor);
        assertEquals(350, tank.getAllowedMoveInterval());
    }

    @Test
    public void getAllowedFireInterval_AntiGravThenFusionReactor_300() {
        tank.pickupPowerUp(PowerUpType.AntiGrav);
        tank.pickupPowerUp(PowerUpType.FusionReactor);
        assertEquals(300, tank.getAllowedFireInterval());
    }

    // Fusion Reactor then AntiGrav
    @Test
    public void getAllowedMoveInterval_FusionReactorThenAntiGrav_300() {
        tank.pickupPowerUp(PowerUpType.FusionReactor);
        tank.pickupPowerUp(PowerUpType.AntiGrav);
        assertEquals(300, tank.getAllowedMoveInterval());
    }

    @Test
    public void getAllowedFireInterval_FusionReactorThenAntiGrav_350() {
        tank.pickupPowerUp(PowerUpType.FusionReactor);
        tank.pickupPowerUp(PowerUpType.AntiGrav);
        assertEquals(350, tank.getAllowedFireInterval());
    }
}