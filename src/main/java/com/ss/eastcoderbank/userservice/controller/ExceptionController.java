package com.ss.eastcoderbank.userservice.controller;

import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.userservice.controller.ExceptionMessage.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(DuplicateConstraintsException.class)
    public ResponseEntity<ErrorMessage> duplicateConstraints(DuplicateConstraintsException exception) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.CONFLICT.toString(), exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> userValidationError(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.toString());
        exception.getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        logger.info(errors.toString());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
