package com.exadel.sandbox.team2.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UserNotFoundException extends ServiceException {

    private static final String ERROR_MESSAGE = "error.user_not_found";

    public UserNotFoundException(Long id) {
        super(ERROR_MESSAGE, id);
    }
}
