package edu.unh.cs.cs619.bulletzone.model.commands;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.EntityDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.events.SpawnEvent;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import org.greenrobot.eventbus.EventBus;

import java.util.Optional;

public class EjectPowerUpCommand implements Command {
    long entityId;
    public EjectPowerUpCommand(long entityId) {
        this.entityId = entityId;
    }

    /**
     *
     * @param entity entity ejecting the power-up
     * @return if ejected
     * @throws EntityDoesNotExistException
     */
    @Override
    public Boolean execute(PlayableEntity entity) throws EntityDoesNotExistException {
        System.out.println("Currently ejecting powerup");
        if (entity == null) {
            throw new EntityDoesNotExistException(entityId);
        }

        Optional<PowerUpEntity> powerUp = entity.dropPowerUp();
        if (powerUp.isEmpty()) {
            return false;
        }

        if (entity.getParent().getEntity() instanceof Dropship dropship) {
            // Unit in dropship, transfer power-up

            dropship.pickupPowerUp(powerUp.get().getType());
        } else {
            // vehicle is not docked, eject to surrounding tile if possible
            if (powerUp.get().getType() == null) {
                return false;
            }

            System.out.println("Server is ejecting " + powerUp.get().getType());

            FieldHolder parent = entity.getParent();
            FieldHolder availableField;
            // check in front of entity to allow for strategic power-up ejection
            if ((availableField = parent.getNeighbor(entity.getDirection())) != null && !availableField.isPresent()) {
                powerUp.get().setParent(availableField);
                availableField.clearField();
                availableField.setFieldEntity(powerUp.get());
                powerUp.get().setPos(availableField.getPosition());
                switch (powerUp.get().getType()) {
                    case AntiGrav -> availableField.getTerrain().setPresentItem(2); // 1 thing, 2 anti, 3 is fusion.
                    case FusionReactor -> availableField.getTerrain().setPresentItem(3);
                    case Thingamajig -> availableField.getTerrain().setPresentItem(1);
                    case PoweredDrill -> availableField.getTerrain().setPresentItem(4);
                    case DeflectorShield -> availableField.getTerrain().setPresentItem(5);
                    case AutomatedRepairKit -> availableField.getTerrain().setPresentItem(6);
                }
                EventBus.getDefault().post(new SpawnEvent(powerUp.get().getIntValue(), powerUp.get().getPos()));
                System.out.println("Ejecting in front");
                return true;
            }

            // check all surrounding locations
            if ((availableField = parent.getNeighbor(Direction.Up)) != null && !availableField.isPresent()) {
                powerUp.get().setParent(availableField);
                availableField.clearField();
                availableField.setFieldEntity(powerUp.get());
                powerUp.get().setPos(availableField.getPosition());
                switch (powerUp.get().getType()) {
                    case AntiGrav -> availableField.getTerrain().setPresentItem(2); // 1 thing, 2 anti, 3 is fusion.
                    case FusionReactor -> availableField.getTerrain().setPresentItem(3);
                    case Thingamajig -> availableField.getTerrain().setPresentItem(1);
                }
                EventBus.getDefault().post(new SpawnEvent(powerUp.get().getIntValue(), powerUp.get().getPos()));
                System.out.println("Ejecting up");
                return true;
            } else if ((availableField = parent.getNeighbor(Direction.Down)) != null && !availableField.isPresent()) {
                powerUp.get().setParent(availableField);
                availableField.clearField();
                availableField.setFieldEntity(powerUp.get());
                powerUp.get().setPos(availableField.getPosition());
                switch (powerUp.get().getType()) {
                    case AntiGrav -> availableField.getTerrain().setPresentItem(2); // 1 thing, 2 anti, 3 is fusion.
                    case FusionReactor -> availableField.getTerrain().setPresentItem(3);
                    case Thingamajig -> availableField.getTerrain().setPresentItem(1);
                }
                EventBus.getDefault().post(new SpawnEvent(powerUp.get().getIntValue(), powerUp.get().getPos()));
                System.out.println("Ejecting down");
                return true;
            } else if ((availableField = parent.getNeighbor(Direction.Left)) != null && !availableField.isPresent()) {
                powerUp.get().setParent(availableField);
                availableField.clearField();
                availableField.setFieldEntity(powerUp.get());
                powerUp.get().setPos(availableField.getPosition());
                switch (powerUp.get().getType()) {
                    case AntiGrav -> availableField.getTerrain().setPresentItem(2); // 1 thing, 2 anti, 3 is fusion.
                    case FusionReactor -> availableField.getTerrain().setPresentItem(3);
                    case Thingamajig -> availableField.getTerrain().setPresentItem(1);
                }
                EventBus.getDefault().post(new SpawnEvent(powerUp.get().getIntValue(), powerUp.get().getPos()));
                System.out.println("Ejecting left");
                return true;
            } else if ((availableField = parent.getNeighbor(Direction.Right)) != null && !availableField.isPresent()) {
                powerUp.get().setParent(availableField);
                availableField.clearField();
                availableField.setFieldEntity(powerUp.get());
                powerUp.get().setPos(availableField.getPosition());
                switch (powerUp.get().getType()) {
                    case AntiGrav -> availableField.getTerrain().setPresentItem(2); // 1 thing, 2 anti, 3 is fusion.
                    case FusionReactor -> availableField.getTerrain().setPresentItem(3);
                    case Thingamajig -> availableField.getTerrain().setPresentItem(1);
                }
                EventBus.getDefault().post(new SpawnEvent(powerUp.get().getIntValue(), powerUp.get().getPos()));
                System.out.println("Ejecting right");
                return true;
            } else { // no tile available, destroy
                System.out.println("Ejecting failed to eject");
                return false;
            }
        }
        return null;
    }
}
