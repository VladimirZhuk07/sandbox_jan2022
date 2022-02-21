package com.exadel.sandbox.team2.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    private Object data;

    public ServiceException(String message, Object data) {
        super(message);
        this.data = data;
    }
}
