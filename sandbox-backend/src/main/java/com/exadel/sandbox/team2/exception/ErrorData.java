package com.exadel.sandbox.team2.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorData {
    private final String message;
    private final Object data;

    public ErrorData(String message) {
        this(message, null);
    }

    public ErrorData(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
