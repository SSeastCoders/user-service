package com.ss.eastcoderbank.userservice.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> genericExceptionHandler(Exception e) {
        System.out.printf("An unknown error occurred.",  e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }





}
