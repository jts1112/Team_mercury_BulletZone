package edu.unh.cs.cs619.bulletzone.repository;

import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.GameBoardBuilder;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
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
        Assert.assertTrue(dropship.getDirection() == Direction.Up);
        assertNotNull(dropship.getParent());
    }
    /*
    @Test
    public void testTurn() throws Exception {
        Tank tank = repo.join("");
        Assert.assertNotNull(tank);
        Assert.assertTrue(tank.getId() >= 0);
        Assert.assertNotNull(tank.getDirection());
        Assert.assertTrue(tank.getDirection() == Direction.Up);
        Assert.assertNotNull(tank.getParent());

        Assert.assertTrue(repo.turn(tank.getId(), Direction.Right));
        Assert.assertTrue(tank.getDirection() == Direction.Right);

        thrown.expect(TankDoesNotExistException.class);
        thrown.expectMessage("Tank '1000' does not exist");
        repo.turn(1000, Direction.Right);
    } */

    @Test
    public void testMove() throws Exception {

    }

    @Test
    public void testFire() throws Exception {

    }

    @Test
    public void testLeave() throws Exception {

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
