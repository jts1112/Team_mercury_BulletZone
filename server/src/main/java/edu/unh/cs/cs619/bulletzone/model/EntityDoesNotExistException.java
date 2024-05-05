package edu.unh.cs.cs619.bulletzone.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public final class EntityDoesNotExistException extends Exception {
    public EntityDoesNotExistException(Long entityId) {
        super(String.format("Entitiy with id '%d' does not exist", entityId));
    }
}
