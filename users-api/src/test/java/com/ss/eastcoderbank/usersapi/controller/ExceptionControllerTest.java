package com.ss.eastcoderbank.usersapi.controller;

import com.ss.eastcoderbank.core.exeception.response.ErrorMessage;
import com.ss.eastcoderbank.usersapi.service.customexceptions.DuplicateConstraintsException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExceptionControllerTest {
    @Test
    void testDuplicateContraints() {
        ExceptionController exceptionController = new ExceptionController();
        ResponseEntity<ErrorMessage> actualDuplicateContraintsResult = exceptionController
                .duplicateConstraints(new DuplicateConstraintsException("An error occurred"));
        assertTrue(actualDuplicateContraintsResult.getHeaders().isEmpty());
        assertTrue(actualDuplicateContraintsResult.hasBody());
        assertEquals(HttpStatus.CONFLICT, actualDuplicateContraintsResult.getStatusCode());
        ErrorMessage body = actualDuplicateContraintsResult.getBody();
        assertEquals("409 CONFLICT", body.getStatus());
        assertEquals("An error occurred", body.getMessage());
    }

    @Test
    void testDuplicateContraints2() {
        ExceptionController exceptionController = new ExceptionController();

        DuplicateConstraintsException duplicateConstraintsException = new DuplicateConstraintsException(
                "An error occurred");
        duplicateConstraintsException.addSuppressed(new Throwable());
        ResponseEntity<ErrorMessage> actualDuplicateContraintsResult = exceptionController
                .duplicateConstraints(duplicateConstraintsException);
        assertTrue(actualDuplicateContraintsResult.getHeaders().isEmpty());
        assertTrue(actualDuplicateContraintsResult.hasBody());
        assertEquals(HttpStatus.CONFLICT, actualDuplicateContraintsResult.getStatusCode());
        ErrorMessage body = actualDuplicateContraintsResult.getBody();
        assertEquals("409 CONFLICT", body.getStatus());
        assertEquals("An error occurred", body.getMessage());
    }

    @Test
    void testUserValidationError() {
        ExceptionController exceptionController = new ExceptionController();
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValidException.getFieldErrors()).thenReturn(new ArrayList<FieldError>());
        ResponseEntity<Map<String, String>> actualUserValidationErrorResult = exceptionController
                .userValidationError(methodArgumentNotValidException);
        assertEquals(2, actualUserValidationErrorResult.getBody().size());
        assertEquals("<400 BAD_REQUEST Bad Request,{message=, status=400 BAD_REQUEST},[]>",
                actualUserValidationErrorResult.toString());
        assertTrue(actualUserValidationErrorResult.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualUserValidationErrorResult.getStatusCode());
        assertTrue(actualUserValidationErrorResult.getHeaders().isEmpty());
        verify(methodArgumentNotValidException).getFieldErrors();
    }

    @Test
    void testUserValidationError2() {
        ExceptionController exceptionController = new ExceptionController();

        ArrayList<FieldError> fieldErrorList = new ArrayList<FieldError>();
        fieldErrorList.add(new FieldError("Object Name", "Field", "Default Message"));
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrorList);
        ResponseEntity<Map<String, String>> actualUserValidationErrorResult = exceptionController
                .userValidationError(methodArgumentNotValidException);
        assertEquals(3, actualUserValidationErrorResult.getBody().size());
        assertEquals("<400 BAD_REQUEST Bad Request,{Field=Default Message, message= Default Message, status=400"
                + " BAD_REQUEST},[]>", actualUserValidationErrorResult.toString());
        assertTrue(actualUserValidationErrorResult.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualUserValidationErrorResult.getStatusCode());
        assertTrue(actualUserValidationErrorResult.getHeaders().isEmpty());
        verify(methodArgumentNotValidException).getFieldErrors();
    }

    @Test
    void testUserValidationError3() {
        ExceptionController exceptionController = new ExceptionController();

        ArrayList<FieldError> fieldErrorList = new ArrayList<FieldError>();
        fieldErrorList.add(new FieldError("Object Name", "Field", "Default Message"));
        fieldErrorList.add(new FieldError("Object Name", "Field", "Default Message"));
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrorList);
        ResponseEntity<Map<String, String>> actualUserValidationErrorResult = exceptionController
                .userValidationError(methodArgumentNotValidException);
        assertEquals(3, actualUserValidationErrorResult.getBody().size());
        assertEquals(
                "<400 BAD_REQUEST Bad Request,{Field=Default Message, message= Default Message Default Message, status=400"
                        + " BAD_REQUEST},[]>",
                actualUserValidationErrorResult.toString());
        assertTrue(actualUserValidationErrorResult.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualUserValidationErrorResult.getStatusCode());
        assertTrue(actualUserValidationErrorResult.getHeaders().isEmpty());
        verify(methodArgumentNotValidException).getFieldErrors();
    }

    @Test
    void testUserValidationError4() {
        ExceptionController exceptionController = new ExceptionController();
        FieldError fieldError = mock(FieldError.class);
        when(fieldError.getDefaultMessage()).thenThrow(new DuplicateConstraintsException("An error occurred"));
        when(fieldError.getField()).thenReturn("foo");

        ArrayList<FieldError> fieldErrorList = new ArrayList<FieldError>();
        fieldErrorList.add(fieldError);
        fieldErrorList.add(null);
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrorList);
        assertThrows(DuplicateConstraintsException.class,
                () -> exceptionController.userValidationError(methodArgumentNotValidException));
        verify(methodArgumentNotValidException).getFieldErrors();
        verify(fieldError).getDefaultMessage();
        verify(fieldError).getField();
    }




}

