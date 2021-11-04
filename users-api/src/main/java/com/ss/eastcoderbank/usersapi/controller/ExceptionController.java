package com.ss.eastcoderbank.usersapi.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.core.JsonParseException;
import com.ss.eastcoderbank.core.exeception.UserNotFoundException;
import com.ss.eastcoderbank.core.exeception.response.ErrorMessage;
import com.ss.eastcoderbank.usersapi.service.CustomExceptions.DuplicateConstraintsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(DuplicateConstraintsException.class)
    public ResponseEntity<ErrorMessage> duplicateConstraints(DuplicateConstraintsException exception) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.CONFLICT.toString(), exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> userValidationError(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.toString());
        errors.put("message", "");
        exception.getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            errors.put("message", errors.get("message") + " " + errorMessage);
        });
        log.info(errors.toString());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> constraintValidation(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.toString());
        exception.getConstraintViolations().forEach(fieldError -> {
            String fieldName = fieldError.getPropertyPath().toString();
            String errorMessage = fieldError.getMessage();
            errors.put(fieldName, errorMessage);
        });
        log.info(errors.toString());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> forbidden(AccessDeniedException exception) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.FORBIDDEN.toString(), exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorMessage> jsonParseFailure(JsonParseException exception) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), "Not valid json. " +  exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> noUserFound(UserNotFoundException exception) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.toString(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorMessage> jwtExpired(TokenExpiredException exception) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.FORBIDDEN.toString(), exception.getMessage()), HttpStatus.FORBIDDEN);
    }

}
