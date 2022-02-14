package com.exadel.sandbox.team2.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorData {

    private String message;

    private Object data;
}
