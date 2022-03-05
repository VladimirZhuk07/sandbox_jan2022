package com.exadel.sandbox.team2.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
