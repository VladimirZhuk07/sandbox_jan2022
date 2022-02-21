package com.exadel.sandbox.team2.exception;

public class RoleNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "error.role_not_found";

    public RoleNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public RoleNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
