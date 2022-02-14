package com.exadel.sandbox.team2.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "error.role_not_found";

    public RoleNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
