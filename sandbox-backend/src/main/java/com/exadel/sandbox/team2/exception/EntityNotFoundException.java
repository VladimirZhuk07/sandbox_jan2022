package com.exadel.sandbox.team2.exception;

public class EntityNotFoundException extends ServiceException {

    private static final String ERROR_MESSAGE = "error.entity_not_found";

    public EntityNotFoundException(Long id) {
        super(ERROR_MESSAGE, id);
    }

    public EntityNotFoundException(String errorMessage, Long id) {
        super(errorMessage, id);
    }
}
