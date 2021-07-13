package com.ss.eastcoderbank.userservice.controller;

import com.ss.eastcoderbank.userservice.controller.ExceptionMessage.ErrorMessage;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException;
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
public class ExceotionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceotionController.class);

    @ExceptionHandler(DuplicateConstraintsException.class)
    public ResponseEntity<ErrorMessage> duplicateContraints(DuplicateConstraintsException exception) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.CONFLICT.toString(), exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> userValidationError(MethodArgumentNotValidException exception) {
        Map<String, String> erros = new HashMap<>();
        erros.put("status", HttpStatus.BAD_REQUEST.toString());
        exception.getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            erros.put(fieldName, errorMessage);
        });
        logger.info(erros.toString());
        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
    }

}
