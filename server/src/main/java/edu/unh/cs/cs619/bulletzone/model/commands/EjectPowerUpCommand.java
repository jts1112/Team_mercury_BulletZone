package edu.unh.cs.cs619.bulletzone.model.commands;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;
import edu.unh.cs.cs619.bulletzone.model.entities.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.entities.PlayableEntity;
import edu.unh.cs.cs619.bulletzone.model.events.SpawnEvent;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpComponent;
import edu.unh.cs.cs619.bulletzone.model.powerUps.PowerUpEntity;
import org.greenrobot.eventbus.EventBus;

import java.util.Optional;
import java.util.OptionalLong;

public class EjectPowerUpCommand implements Command {
    long entityId;
    public EjectPowerUpCommand(long entityId) {
        this.entityId = entityId;
    }
    @Override
    public Boolean execute(PlayableEntity entity) throws TankDoesNotExistException {
        if (entity == null) {
            throw new TankDoesNotExistException(entityId);
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

            FieldHolder parent = entity.getParent();
            FieldHolder availableField;
            if ((availableField = parent.getNeighbor(Direction.Up)) != null && !availableField.isPresent()) {
                powerUp.get().setParent(availableField);
                EventBus.getDefault().post(new SpawnEvent(powerUp.get().getIntValue(), powerUp.get().getPos()));
                return true;
            } else if ((availableField = parent.getNeighbor(Direction.Down)) != null && !availableField.isPresent()) {
                powerUp.get().setParent(availableField);
                EventBus.getDefault().post(new SpawnEvent(powerUp.get().getIntValue(), powerUp.get().getPos()));
                return true;
            } else if ((availableField = parent.getNeighbor(Direction.Left)) != null && !availableField.isPresent()) {
                powerUp.get().setParent(availableField);
                EventBus.getDefault().post(new SpawnEvent(powerUp.get().getIntValue(), powerUp.get().getPos()));
                return true;
            } else if ((availableField = parent.getNeighbor(Direction.Right)) != null && !availableField.isPresent()) {
                powerUp.get().setParent(availableField);
                EventBus.getDefault().post(new SpawnEvent(powerUp.get().getIntValue(), powerUp.get().getPos()));
                return true;
            } else { // no tile available, destroy
                return false;
            }
        }
        return null;
    }
}
