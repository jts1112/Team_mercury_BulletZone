package edu.unh.cs.cs619.bulletzone.model.commands;
import static com.google.common.base.Preconditions.checkNotNull;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.EntityDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.Miner;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
import edu.unh.cs.cs619.bulletzone.model.events.DamageEvent;
import edu.unh.cs.cs619.bulletzone.model.events.MoveEvent;
import edu.unh.cs.cs619.bulletzone.model.events.RemovalEvent;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import edu.unh.cs.cs619.bulletzone.repository.InMemoryGameRepository;
import org.greenrobot.eventbus.EventBus;

import edu.unh.cs.cs619.bulletzone.model.events.TurnEvent;

public class MoveCommand implements Command {
    private long entityId;
    private Direction direction;

    /**
     * Move Command To initialize variables needed to execute
     * @param entityId passed in tankID
     * @param direction passed in Direction
     */
    public MoveCommand(long entityId, Direction direction){
        this.entityId = entityId;
        this.direction = direction;
    }

    /**
     * MoveCommand Execute to run code from move() that was in InMemoryGameRepository
     * @return returns True if Successes and False if Failure.
     * @throws EntityDoesNotExistException if tank does not exist.
     */
    @Override
    public Boolean execute(PlayableEntity entity) throws EntityDoesNotExistException {

        // Find tank
        if (entity == null) {
            throw new EntityDoesNotExistException(entityId);
        }

        // Check if the time from the last move is too short
        long millis = System.currentTimeMillis();
        if(millis < entity.getLastMoveTime())
            return false;


        // Rotate tank if not moving forwards or backwards
        Direction currentDir = entity.getDirection();
        Direction desiredDirection = this.direction;
        int current = Byte.toUnsignedInt(Direction.toByte(currentDir));
        if (current == 0) {
            current = 8;
        }

        if (direction != Direction.Above && direction != Direction.Below) {
            int desired = Byte.toUnsignedInt(Direction.toByte(desiredDirection));
            if (desired == ((current + 2) % 8) || desired == ((current - 2) % 8)
                    || entity.isImmobile()) {
                // Set new direction
                entity.setDirection(desiredDirection);

                // Post new TurnEvent
                EventBus.getDefault().post(new TurnEvent(entity.getIntValue(), currentDir,
                        entity.getDirection(), entity.getPosition()));

                // Set the next valid move time
                entity.setLastMoveTime(millis + entity.getAllowedMoveInterval());
                return true;
            }
        }

        FieldHolder parent = entity.getParent();
        FieldHolder nextField = parent.getNeighbor(direction);

        double difficulty = nextField.getTerrain().getDifficulty(entity); // TODO testing the difficulty

        checkNotNull(parent.getNeighbor(direction), "Neighbor is not available");
        boolean isCompleted = false;

        if (!nextField.isPresent() && (difficulty > 0)) {  // If nextField is empty
            System.out.println("Move Case 1 !nextField.isPresent()=" + !nextField.isPresent() + "   difficulty=" + difficulty);
            int oldPos = entity.getPosition();
            FieldEntity parentEntity = parent.getEntity();
            if (parentEntity instanceof Dropship dropship) {
                if (entity instanceof Tank tank) {
                    dropship.undockTank(tank);
                } else if (entity instanceof Miner miner) {
                    dropship.undockMiner(miner);
                }
            } else {
                parent.clearField();
            }

            nextField.setFieldEntity(entity);
            entity.setParent(nextField);

            int newPos = entity.getPosition();
            EventBus.getDefault().post(new MoveEvent(entity.getIntValue(), oldPos, newPos));
            isCompleted = true;
            // TODO remove difficulty
            entity.setLastMoveTime(millis + (long) (entity.getAllowedMoveInterval() * difficulty));
        } else if (nextField.getEntity() != null) {
            System.out.println("Move Case 2");
            FieldEntity nextEntity = nextField.getEntity(); // TODO OLD



            if (nextEntity instanceof Dropship dropship) { // Move the entity into the Dropship
                System.out.println("Move Case 2a");
                parent.clearField();
                if (entity instanceof Tank tank) {
                    dropship.dockTank(tank);
                } else if (entity instanceof Miner miner) {
                    dropship.dockMiner(miner);
                }

                int oldPos = entity.getPosition();
                entity.setParent(nextField);

                int newPos = dropship.getPosition();
                EventBus.getDefault().post(new MoveEvent(entity.getIntValue(), oldPos, newPos));
                isCompleted = true;
                entity.setLastMoveTime(millis + entity.getAllowedMoveInterval());
                dropship.repairUnits();
            } else if (nextEntity.isUsable()) {
                System.out.println("In move command. Power-up type is: " + ((PowerUpEntity) nextEntity).getType());
                entity.pickupPowerUp(((PowerUpEntity) nextEntity).getType());

                // reduce power-up count due to pickup
                InMemoryGameRepository repo = InMemoryGameRepository.getInstance();
                repo.decrementPowerUpCount();

                int oldPos = entity.getPosition();

                nextEntity.getParent().getTerrain().setPresentItem(0); // remove the current powerUp
                nextEntity.getParent().clearField();
                nextEntity.setParent(null);

                entity.getParent().clearField();
                entity.setParent(nextField);
                nextField.setFieldEntity(entity);

                int newPos = entity.getPosition();
                EventBus.getDefault().post(new MoveEvent(entity.getIntValue(), oldPos, newPos));
                isCompleted = true;
                entity.setLastMoveTime(millis + entity.getAllowedMoveInterval());
            } else {
                System.out.println("Move isCompleted false");
                // hit the entity and the next entity for appropriate damage
                nextEntity.hit(entity.getHitDamage());
                entity.hit(entity.getSelfHitDamage());
                EventBus.getDefault().post(new DamageEvent(nextEntity.getPosition(), nextEntity.getIntValue()));
                EventBus.getDefault().post(new DamageEvent(entity.getPosition(), entity.getIntValue()));

                if (nextEntity.getLife() <= 0) {
                    nextEntity.getParent().clearField();
                    EventBus.getDefault().post(new RemovalEvent(nextEntity.getPosition()));
                }
                if (entity.getLife() <= 0) {
                    entity.getParent().clearField();
                    EventBus.getDefault().post(new RemovalEvent(entity.getPosition()));
                }
                isCompleted = false;
            }
        }

        return isCompleted;
    }

    /**
     * MoveCommand Execute to run code from move() that was in InMemoryGameRepository
     * @return returns True if Successes and False if Failure.
     * @throws EntityDoesNotExistException if tank does not exist.
     */
    public Boolean execute2(PlayableEntity entity, Game game) throws EntityDoesNotExistException {

        // Find tank
        if (entity == null) {
            throw new EntityDoesNotExistException(entityId);
        }

        // Check if the time from the last move is too short
        long millis = System.currentTimeMillis();
        if(millis < entity.getLastMoveTime())
            return false;


        // Rotate tank if not moving forwards or backwards
        Direction currentDir = entity.getDirection();
        Direction desiredDirection = this.direction;
        int current = Byte.toUnsignedInt(Direction.toByte(currentDir));
        if (current == 0) {
            current = 8;
        }

        if (direction != Direction.Above && direction != Direction.Below) {
            int desired = Byte.toUnsignedInt(Direction.toByte(desiredDirection));
            if (desired == ((current + 2) % 8) || desired == ((current - 2) % 8)
                    || entity instanceof Dropship) {
                // Set new direction
                entity.setDirection(desiredDirection);

                // Post new TurnEvent
                EventBus.getDefault().post(new TurnEvent(entity.getIntValue(), currentDir,
                        entity.getDirection(), entity.getPosition()));

                // Set the next valid move time
                entity.setLastMoveTime(millis + entity.getAllowedMoveInterval());
                return true;
            }
        }

        FieldHolder parent = entity.getParent();
        FieldHolder nextField = parent.getNeighbor(direction);

        double difficulty = nextField.getTerrain().getDifficulty(entity); // TODO testing the difficulty

        checkNotNull(parent.getNeighbor(direction), "Neighbor is not available");
        boolean isCompleted = false;

        if (!nextField.isPresent() && (difficulty > 0)) {  // If nextField is empty
            System.out.println("Move Case 1 !nextField.isPresent()=" + !nextField.isPresent() + "   difficulty=" + difficulty);
            int oldPos = entity.getPosition();
            FieldEntity parentEntity = parent.getEntity();
            if (parentEntity instanceof Dropship dropship) {
                if (entity instanceof Tank tank) {
                    dropship.undockTank(tank);
                } else if (entity instanceof Miner miner) {
                    dropship.undockMiner(miner);
                }
            } else {
                parent.clearField();
            }

            nextField.setFieldEntity(entity);
            entity.setParent(nextField);

            int newPos = entity.getPosition();
            EventBus.getDefault().post(new MoveEvent(entity.getIntValue(), oldPos, newPos));
            isCompleted = true;
            // TODO remove difficulty
            entity.setLastMoveTime(millis + (long) (entity.getAllowedMoveInterval() * difficulty));
        } else if (nextField.getEntity() != null) {
            System.out.println("Move Case 2");
            FieldEntity nextEntity = nextField.getEntity(); // TODO OLD



            if (nextEntity instanceof Dropship dropship) { // Move the entity into the Dropship
                System.out.println("Move Case 2a");
                parent.clearField();
                if (entity instanceof Tank tank) {
                    dropship.dockTank(tank);
                } else if (entity instanceof Miner miner) {
                    dropship.dockMiner(miner);
                }

                int oldPos = entity.getPosition();
                entity.setParent(nextField);

                int newPos = dropship.getPosition();
                EventBus.getDefault().post(new MoveEvent(entity.getIntValue(), oldPos, newPos));
                isCompleted = true;
                entity.setLastMoveTime(millis + entity.getAllowedMoveInterval());
                dropship.repairUnits();
            } else if (nextEntity instanceof PowerUpEntity) {
                System.out.println("In move command. Power-up type is: " + ((PowerUpEntity) nextEntity).getType());
                entity.pickupPowerUp(((PowerUpEntity) nextEntity).getType());

                // reduce power-up count due to pickup
                game.decrementnumPowerups();

                int oldPos = entity.getPosition();

                nextEntity.getParent().getTerrain().setPresentItem(0); // remove the current powerUp
                nextEntity.getParent().clearField();
                nextEntity.setParent(null);

                entity.getParent().clearField();
                entity.setParent(nextField);
                nextField.setFieldEntity(entity);

                int newPos = entity.getPosition();
                EventBus.getDefault().post(new MoveEvent(entity.getIntValue(), oldPos, newPos));
                isCompleted = true;
                entity.setLastMoveTime(millis + entity.getAllowedMoveInterval());
            } else {
                System.out.println("Move isCompleted false");
                // hit the entity and the next entity for appropriate damage
                nextEntity.hit(entity.getHitDamage());
                entity.hit(entity.getSelfHitDamage());
                EventBus.getDefault().post(new DamageEvent(nextEntity.getPosition(), nextEntity.getIntValue()));
                EventBus.getDefault().post(new DamageEvent(entity.getPosition(), entity.getIntValue()));

                if (nextEntity.getLife() <= 0) {
                    nextEntity.getParent().clearField();
                    EventBus.getDefault().post(new RemovalEvent(nextEntity.getPosition()));
                }
                if (entity.getLife() <= 0) {
                    entity.getParent().clearField();
                    EventBus.getDefault().post(new RemovalEvent(entity.getPosition()));
                }
                isCompleted = false;
            }
        }

        return isCompleted;
    }
}
