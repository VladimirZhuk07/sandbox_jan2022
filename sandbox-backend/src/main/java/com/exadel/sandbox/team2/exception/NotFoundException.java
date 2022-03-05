package com.exadel.sandbox.team2.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
