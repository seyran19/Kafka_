package com.example.productmicroservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j(topic = "ErrorHandlerAdvice")
public class ErrorHandlerAdvice {


    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorMessage badRequestException(Exception exception) {
        String message = exception.getMessage();
        log.error("400 {}", message);
        return new ErrorMessage(new Date(), message);
    }
}
