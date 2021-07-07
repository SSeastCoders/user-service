package com.ss.eastcoderbank.userservice.controller;

import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DuplicateConstraintsException.class)
    public ResponseEntity<Map<String, String>> duplicateConstraints(DuplicateConstraintsException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("status", "409");
        error.put("message", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> userValidationError(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("status", "400");
        exception.getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
