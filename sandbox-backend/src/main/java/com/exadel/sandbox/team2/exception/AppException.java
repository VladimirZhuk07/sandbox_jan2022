package com.exadel.sandbox.team2.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AppException extends RuntimeException {

    private Object data;

    public AppException(String message, Object data) {
        super(message);
        this.data = data;
    }
}
