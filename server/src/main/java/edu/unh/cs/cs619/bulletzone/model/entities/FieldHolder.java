package edu.unh.cs.cs619.bulletzone.model.entities;

import java.util.Optional;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

import edu.unh.cs.cs619.bulletzone.datalayer.terrain.Terrain;
import edu.unh.cs.cs619.bulletzone.model.Direction;

public class FieldHolder {

    private final Map<Direction, FieldHolder> neighbors = new HashMap<>();
    private Optional<FieldEntity> entityHolder = Optional.empty();
    private final int position;
    private Terrain terrain;

    public FieldHolder(int pos) {
        this.position = pos;
    }

    public int getPosition() {return position;}

    public void addNeighbor(Direction direction, FieldHolder fieldHolder) {
        neighbors.put(checkNotNull(direction), checkNotNull(fieldHolder));
    }

    public FieldHolder getNeighbor(Direction direction) {
        return neighbors.get(checkNotNull(direction,
                "Direction cannot be null."));
    }

    public boolean isPresent() {
        return entityHolder.isPresent();
    }

    public FieldEntity getEntity() {
        return entityHolder.get();
    }

    public void setFieldEntity(FieldEntity entity) {
        entityHolder = Optional.of(checkNotNull(entity,
                "FieldEntity cannot be null."));
    }

    public void clearField() {
        if (entityHolder.isPresent()) {
            entityHolder = Optional.empty();
        }
    }

    public Terrain getTerrain(){
        return this.terrain;
    }

    public void setTerrain(Terrain terrain){
        this.terrain = terrain;
    }
}
