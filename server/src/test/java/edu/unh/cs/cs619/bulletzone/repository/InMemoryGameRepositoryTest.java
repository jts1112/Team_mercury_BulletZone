package edu.unh.cs.cs619.bulletzone.repository;

import edu.unh.cs.cs619.bulletzone.datalayer.user.GameUser;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class InMemoryGameRepositoryTest {
    private static final int FIELD_DIM = 16;



    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @InjectMocks
    InMemoryGameRepository repo;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testJoin() throws Exception {
        Dropship dropship = repo.join("");
        assertNotNull(dropship);
        Assert.assertTrue(dropship.getId() >= 0);
        assertNotNull(dropship.getDirection());
        assertSame(dropship.getDirection(), Direction.Up);
        assertNotNull(dropship.getParent());
    }

    @Test
    public void testMove() throws Exception {

    }

    @Test
    public void testFire() throws Exception {

    }

    @Test
    public void testLeave() throws Exception {
        Dropship dropship = repo.join("127.0.0.1");
        long minerId = repo.spawnMiner(dropship.getId(), 10);
        long tankId = repo.spawnTank(dropship.getId(), 10);
        long minerId2 = repo.spawnMiner(dropship.getId(), 10);
        long tankId2 = repo.spawnTank(dropship.getId(), 10);
        long minerId3 = repo.spawnMiner(dropship.getId(), 10);
        long tankId3 = repo.spawnTank(dropship.getId(), 10);

        repo.leave(dropship.getId());
        assertNull(repo.getGame().getDropship(dropship.getId()));
        assertNull(repo.getGame().getMiner(minerId));
        assertNull(repo.getGame().getTank(tankId));
        assertNull(repo.getGame().getMiner(minerId2));
        assertNull(repo.getGame().getTank(tankId2));
        assertNull(repo.getGame().getMiner(minerId3));
        assertNull(repo.getGame().getTank(tankId3));
    }

    @Test
    public void Leave_sellPowerUps_ValueCorrect() throws Exception {
        int expectedValue = 0;

        int expectedAntiGravVal = 300;
        int expectedFusionReactorVal = 400;
        int expectedPoweredDrillVal = 400;
        int expectedDeflectorShieldVal = 300;
        int expectedAutoRepairKitVal = 200;

        Game game = repo.getGame();
        Dropship dropship = repo.join("127.0.0.1");
        dropship.pickupPowerUp(PowerUpType.DeflectorShield);
        expectedValue += expectedDeflectorShieldVal;

        DataRepository dataRepo = DataRepositoryFactory.getInstance();
        assertTrue(dataRepo.validateUser("test", "user", dropship.getIp(), true).isPresent());

        GameUser user = dataRepo.getUser(dropship.getIp());
        double originalBal = dataRepo.getBankBalance(user) - 1000; // accounting for the leave fee

        long minerId = repo.spawnMiner(dropship.getId());
        game.getMiner(minerId).pickupPowerUp(PowerUpType.AntiGrav);
        expectedValue += expectedAntiGravVal;

        long tankId = repo.spawnTank(dropship.getId());
        game.getTank(tankId).pickupPowerUp(PowerUpType.FusionReactor);
        expectedValue += expectedFusionReactorVal;
        game.getTank(tankId).pickupPowerUp(PowerUpType.PoweredDrill);
        expectedValue += expectedPoweredDrillVal;

        long minerId2 = repo.spawnMiner(dropship.getId());
        game.getMiner(minerId2).pickupPowerUp(PowerUpType.AntiGrav);
        expectedValue += expectedAntiGravVal;

        long tankId2 = repo.spawnTank(dropship.getId());
        game.getTank(tankId2).pickupPowerUp(PowerUpType.DeflectorShield);
        expectedValue += expectedDeflectorShieldVal;

        long minerId3 = repo.spawnMiner(dropship.getId());
        game.getMiner(minerId3).pickupPowerUp(PowerUpType.AntiGrav);
        expectedValue += expectedAntiGravVal;

        long tankId3 = repo.spawnTank(dropship.getId());
        game.getTank(tankId3).pickupPowerUp(PowerUpType.DeflectorShield);
        expectedValue += expectedDeflectorShieldVal;
        game.getTank(tankId3).pickupPowerUp(PowerUpType.AutomatedRepairKit);
        expectedValue += expectedAutoRepairKitVal;

        repo.leave(dropship.getId());

        assertEquals(expectedValue, (dataRepo.getBankBalance(user) - originalBal), 0.9);
    }

//    @Test
//    public void testFindFreeSpace() {
//        Game game = new Game();
//        Object monitor = new Object();
//        game.getGameBoard().setBoard(new GameBoardBuilder(FIELD_DIM, monitor).inMemoryGameReposiryInitialize().build());
//
//        FieldHolder startingPoint = game.getGameBoard().getBoard().get(FIELD_DIM / 2 * FIELD_DIM + FIELD_DIM / 2);
//
//        FieldHolder freeSpace = findFreeSpace(startingPoint);
//        assertNotNull(freeSpace);
//        assertFalse(freeSpace.isPresent());
//
//        int startIndex = game.getGameBoard().getBoard().indexOf(startingPoint);
//        int x = startIndex % FIELD_DIM;
//        int y = startIndex / FIELD_DIM;
//        for (int i = -1; i <= 1; i++) {
//            for (int j = -1; j <= 1; j++) {
//                int index = (y + i) * FIELD_DIM + (x + j);
//                if (index >= 0 && index < game.getGameBoard().getBoard().size()) {
//                    game.getGameBoard().getBoard().get(index).setFieldEntity(new Tank(1L, Direction.Up, ""));
//                }
//            }
//        }
//        freeSpace = findFreeSpace(startingPoint);
//        assertNull(freeSpace);
//    }
}
