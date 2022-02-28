package com.exadel.sandbox.team2.exception.handler;

import com.exadel.sandbox.team2.exception.ErrorData;
import com.exadel.sandbox.team2.exception.ResourceNotFoundException;
import com.exadel.sandbox.team2.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.bind.ValidationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleAppException(ServiceException exception) {
        ErrorData data = new ErrorData();
        data.setData(exception.getData());
        data.setMessage(exception.getMessage());
        log.error("message = {}, data = {}", exception.getMessage(), exception.getData());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(Exception exception) {
        ErrorData data = new ErrorData();
        data.setMessage(exception.getMessage());
        log.error("Exception: " + data.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(ValidationException exception) {
        ErrorData data = new ErrorData();
        data.setMessage(exception.getMessage());
        log.error("Validation Exception: " + data.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(ResourceNotFoundException exception) {
        ErrorData data = new ErrorData();
        data.setMessage(exception.getMessage());
        log.error("Web Module Resource Not Found Exception: " + data.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
