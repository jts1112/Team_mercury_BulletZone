package edu.unh.cs.cs619.bulletzone.model.powerUps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.EntityDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.commands.MoveCommand;
import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.Miner;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;

public class MinerEntityRamTests {

    // Setup all walls around the entity

    // Normal Ram no powerups, Dirt Wall and other vehicle.

    // Ram with Deflector Sheild Dirt and Wall and other vehicle

    // Ram with  PowerDrill Dirt and Wall and other vehicle

    static Game game;
    static Miner miner;

    static Miner enemyMiner;

    static Tank enemyTank;

    static Dropship enemyDropship;

    @Before
    public void setup() {
        game = new Game();
        miner = new Miner(1, Direction.Left,"127.0.0.1");

        // ENEMY
        enemyMiner = new Miner(1, Direction.Left,"127.0.0.1");
        enemyTank = new Tank(1, Direction.Left,"127.0.0.1");
        enemyDropship = new Dropship(1, Direction.Left,"127.0.0.1");
        MockitoAnnotations.initMocks(this);

    }

    @After
    public void cleanup(){
        game = null;
        miner = null;

        enemyMiner = null;
        enemyTank = null;
        enemyDropship = null;
    }

    @Test
    public void MinerRamIntoWall_ValidDamageTest_NoPowerup() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9).setWall(1100,33). // left of entity will be wall.
                build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2); // 34 so 33 would be to the left.
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);


        // Set up initial conditions
        MoveCommand moveCommand = new MoveCommand(miner.getId(), Direction.Left);

        // Execute the command
        moveCommand.execute2(miner, game);

        // verify that the correct damage of miner was done
        assertEquals(95, miner.getLife());
        assertEquals(34,miner.getPosition());
        // verify that the correct damage to wall was done
        if (game.getHolderGrid().get(33).isPresent()) {
            assertEquals(75, game.getHolderGrid().get(33).getEntity().getLife());
        }

    }


    @Test
    public void MinerRamIntoWall_ValidDamageTest_DeflectorSheild() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9).setWall(1100,33). // left of entity will be wall.
                        build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2); // 34 so 33 would be to the left.
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);
        miner.pickupPowerUp(PowerUpType.DeflectorShield);

        // Set up initial conditions
        MoveCommand moveCommand = new MoveCommand(miner.getId(), Direction.Left);

        // Execute the command
        moveCommand.execute2(miner, game);

        // verify that the correct damage of miner was done
        assertEquals(120, miner.getLife());
        assertEquals(34,miner.getPosition());
        // verify that the correct damage to wall was done
        if (game.getHolderGrid().get(33).isPresent()) {
            assertEquals(75, game.getHolderGrid().get(33).getEntity().getLife());
        }

    }


    @Test
    public void MinerRamIntoWall_ValidDamageTest_PowerDrill() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9).setWall(1100,33). // left of entity will be wall.
                        build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2); // 34 so 33 would be to the left.
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);
        miner.pickupPowerUp(PowerUpType.PoweredDrill);

        // Set up initial conditions
        MoveCommand moveCommand = new MoveCommand(miner.getId(), Direction.Left);

        // Execute the command
        moveCommand.execute2(miner, game);

        // verify that the correct damage of miner was done
        assertEquals(108, miner.getLife());
        assertEquals(34,miner.getPosition());
        // verify that the correct damage to wall was done
        if (game.getHolderGrid().get(33).isPresent()) {
            assertEquals(25, game.getHolderGrid().get(33).getEntity().getLife());
        }

    }

    ////////// DIRT WALLL TESTS /////////


    @Test
    public void MinerRamIntoDirtWall_ValidDamageTest_NoPowerup() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9).setDirt(5100,33). // left of entity will be wall.
                        build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2); // 34 so 33 would be to the left.
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);


        // Set up initial conditions
        MoveCommand moveCommand = new MoveCommand(miner.getId(), Direction.Left);

        // Execute the command
        moveCommand.execute2(miner, game);

        // verify that the correct damage of miner was done
        assertEquals(95, miner.getLife());
        assertEquals(34,miner.getPosition());
        // verify that the correct damage to wall was done
        if (game.getHolderGrid().get(33).isPresent()) {
            assertEquals(75, game.getHolderGrid().get(33).getEntity().getLife());
        }

    }


    @Test
    public void MinerRamIntoDirtWall_ValidDamageTest_DeflectorSheild() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9).setDirt(5100,33). // left of entity will be wall.
                        build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2); // 34 so 33 would be to the left.
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);
        miner.pickupPowerUp(PowerUpType.DeflectorShield);

        // Set up initial conditions
        MoveCommand moveCommand = new MoveCommand(miner.getId(), Direction.Left);

        // Execute the command
        moveCommand.execute2(miner, game);

        // verify that the correct damage of miner was done
        assertEquals(120, miner.getLife());
        assertEquals(34,miner.getPosition());
        // verify that the correct damage to wall was done
        if (game.getHolderGrid().get(33).isPresent()) {
            assertEquals(75, game.getHolderGrid().get(33).getEntity().getLife());
        }

    }


    @Test
    public void MinerRamIntoDirtWall_ValidDamageTest_PowerDrill() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9).setDirt(5100,33). // left of entity will be wall.
                        build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2); // 34 so 33 would be to the left.
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        game.addMiner(miner);
        miner.pickupPowerUp(PowerUpType.PoweredDrill);

        // Set up initial conditions
        MoveCommand moveCommand = new MoveCommand(miner.getId(), Direction.Left);

        // Execute the command
        moveCommand.execute2(miner, game);

        // verify that the correct damage of miner was done
        assertEquals(108, miner.getLife());
        assertEquals(34,miner.getPosition());
        // verify that the correct damage to wall was done
        if (game.getHolderGrid().get(33).isPresent()) {
            assertEquals(25, game.getHolderGrid().get(33).getEntity().getLife());
        }

    }


    /// RAMING INTO TANK TESTS /////


    @Test
    public void MinerRamIntoTank_ValidDamageTest_NoPowerup() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9). // left of entity will be tank.
                        build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2); // 34 so 33 would be to the left.
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        // addEnemyTank to the Left
        FieldHolder fieldElement1 = game.getHolderGrid().get(2 * 16 + 1); // 34 so 33 would be to the left.
        fieldElement1.setFieldEntity(enemyTank);
        enemyTank.setParent(fieldElement);


        game.addTank(enemyTank);
        game.addMiner(miner);


        // Set up initial conditions
        MoveCommand moveCommand = new MoveCommand(miner.getId(), Direction.Left);

        // Execute the command
        moveCommand.execute2(miner, game);

        // verify that the correct damage of miner was done
        assertEquals(95, miner.getLife());
        assertEquals(34,miner.getPosition());
        // verify that the correct damage to wall was done
        if (game.getHolderGrid().get(33).isPresent()) {
            assertEquals(75, game.getHolderGrid().get(33).getEntity().getLife());
        }

    }


    @Test
    public void MinerRamIntoTank_ValidDamageTest_DeflectorSheild() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9).
                        build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2); // 34 so 33 would be to the left.
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        // addEnemyTank to the Left
        FieldHolder fieldElement1 = game.getHolderGrid().get(2 * 16 + 1); // 34 so 33 would be to the left.
        fieldElement1.setFieldEntity(enemyTank);
        enemyTank.setParent(fieldElement);
        game.addTank(enemyTank);


        game.addMiner(miner);
        miner.pickupPowerUp(PowerUpType.DeflectorShield);

        // Set up initial conditions
        MoveCommand moveCommand = new MoveCommand(miner.getId(), Direction.Left);

        // Execute the command
        moveCommand.execute2(miner, game);

        // verify that the correct damage of miner was done
        assertEquals(120, miner.getLife());
        assertEquals(34,miner.getPosition());
        // verify that the correct damage to wall was done
        if (game.getHolderGrid().get(33).isPresent()) {
            assertEquals(75, game.getHolderGrid().get(33).getEntity().getLife());
        }

    }


    @Test
    public void MinerRamIntoTank_ValidDamageTest_PowerDrill() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9). // left of entity will be wall.
                        build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2); // 34 so 33 would be to the left.
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        // addEnemyTank to the Left
        FieldHolder fieldElement1 = game.getHolderGrid().get(2 * 16 + 1); // 34 so 33 would be to the left.
        fieldElement1.setFieldEntity(enemyTank);
        enemyTank.setParent(fieldElement);


        game.addTank(enemyTank);



        game.addMiner(miner);
        miner.pickupPowerUp(PowerUpType.PoweredDrill);

        // Set up initial conditions
        MoveCommand moveCommand = new MoveCommand(miner.getId(), Direction.Left);

        // Execute the command
        moveCommand.execute2(miner, game);

        // verify that the correct damage of miner was done
        assertEquals(108, miner.getLife());
        assertEquals(34,miner.getPosition());
        // verify that the correct damage to wall was done
        if (game.getHolderGrid().get(33).isPresent()) {
            assertEquals(25, game.getHolderGrid().get(33).getEntity().getLife());
        }
    }



    // RAMMIN INTO MINER TESTS


    @Test
    public void MinerRamIntoMiner_ValidDamageTest_NoPowerup() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9). // left of entity will be tank.
                        build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2); // 34 so 33 would be to the left.
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        // addEnemyTank to the Left
        FieldHolder fieldElement1 = game.getHolderGrid().get(2 * 16 + 1); // 34 so 33 would be to the left.
        fieldElement1.setFieldEntity(enemyMiner);
        enemyMiner.setParent(fieldElement);


        game.addMiner(enemyMiner);
        game.addMiner(miner);


        // Set up initial conditions
        MoveCommand moveCommand = new MoveCommand(miner.getId(), Direction.Left);

        // Execute the command
        moveCommand.execute2(miner, game);

        // verify that the correct damage of miner was done
        assertEquals(95, miner.getLife());
        assertEquals(34,miner.getPosition());
        // verify that the correct damage to wall was done
        if (game.getHolderGrid().get(33).isPresent()) {
            assertEquals(95, game.getHolderGrid().get(33).getEntity().getLife());
        }

    }


    @Test
    public void MinerRamIntoMiner_ValidDamageTest_DeflectorSheild() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9).
                build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2); // 34 so 33 would be to the left.
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        // addEnemyTank to the Left
        FieldHolder fieldElement1 = game.getHolderGrid().get(2 * 16 + 1); // 34 so 33 would be to the left.
        fieldElement1.setFieldEntity(enemyMiner);
        enemyMiner.setParent(fieldElement);


        game.addMiner(enemyMiner);


        game.addMiner(miner);
        miner.pickupPowerUp(PowerUpType.DeflectorShield);

        // Set up initial conditions
        MoveCommand moveCommand = new MoveCommand(miner.getId(), Direction.Left);

        // Execute the command
        moveCommand.execute2(miner, game);

        // verify that the correct damage of miner was done
        assertEquals(120, miner.getLife());
        assertEquals(34,miner.getPosition());
        // verify that the correct damage to wall was done
        if (game.getHolderGrid().get(33).isPresent()) {
            assertEquals(95, game.getHolderGrid().get(33).getEntity().getLife());
        }

    }


    @Test
    public void MinerRamIntoMiner_ValidDamageTest_PowerDrill() throws EntityDoesNotExistException {

        game.getHolderGrid().clear();
        game.getHolderGrid().addAll(new GameBoardBuilder(16, new Object()).setLayerRowTerrain(0,0, 9).
                setLayerRowTerrain(0,1, 9).
                setLayerRowTerrain(0,2, 9).
                setLayerRowTerrain(0,3, 9).
                setLayerRowTerrain(0,4, 9).
                setLayerRowTerrain(0,5, 9).
                setLayerRowTerrain(0,6, 9).
                setLayerRowTerrain(0,7, 9).
                setLayerRowTerrain(0,8, 9).
                setLayerRowTerrain(0,9, 9).
                setLayerRowTerrain(0,10, 9).
                setLayerRowTerrain(0,11, 9).
                setLayerRowTerrain(0,12, 9).
                setLayerRowTerrain(0,13, 9).
                setLayerRowTerrain(0,14, 9).
                setLayerRowTerrain(0,15, 9). // left of entity will be wall.
                        build());

        FieldHolder fieldElement = game.getHolderGrid().get(2 * 16 + 2); // 34 so 33 would be to the left.
        fieldElement.setFieldEntity(miner);
        miner.setParent(fieldElement);

        // addEnemyTank to the Left
        FieldHolder fieldElement1 = game.getHolderGrid().get(2 * 16 + 1); // 34 so 33 would be to the left.
        fieldElement1.setFieldEntity(enemyMiner);
        enemyMiner.setParent(fieldElement);


        game.addMiner(enemyMiner);



        game.addMiner(miner);
        miner.pickupPowerUp(PowerUpType.PoweredDrill);

        // Set up initial conditions
        MoveCommand moveCommand = new MoveCommand(miner.getId(), Direction.Left);

        // Execute the command
        moveCommand.execute2(miner, game);

        // verify that the correct damage of miner was done
        assertEquals(108, miner.getLife());
        assertEquals(34,miner.getPosition());
        // verify that the correct damage to wall was done
        if (game.getHolderGrid().get(33).isPresent()) {
            assertEquals(45, game.getHolderGrid().get(33).getEntity().getLife());
        }

    }


}
