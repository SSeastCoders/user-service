package com.ss.eastcoderbank.userservice.exceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppExceptionHandlerTest {
    @Test
    public void testGenericExceptionHandler() {
        AppExceptionHandler appExceptionHandler = new AppExceptionHandler();
        ResponseEntity<String> actualGenericExceptionHandlerResult = appExceptionHandler
                .genericExceptionHandler(new Exception("An error occurred"));
        assertEquals("An error occurred", actualGenericExceptionHandlerResult.getBody());
        assertEquals("<500 INTERNAL_SERVER_ERROR Internal Server Error,An error occurred,[]>",
                actualGenericExceptionHandlerResult.toString());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualGenericExceptionHandlerResult.getStatusCode());
        assertTrue(actualGenericExceptionHandlerResult.getHeaders().isEmpty());
    }

    @Test
    public void testGenericExceptionHandler2() {
        AppExceptionHandler appExceptionHandler = new AppExceptionHandler();

        Exception exception = new Exception();
        exception.addSuppressed(new Throwable());
        ResponseEntity<String> actualGenericExceptionHandlerResult = appExceptionHandler.genericExceptionHandler(exception);
        assertNull(actualGenericExceptionHandlerResult.getBody());
        assertEquals("<500 INTERNAL_SERVER_ERROR Internal Server Error,[]>",
                actualGenericExceptionHandlerResult.toString());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualGenericExceptionHandlerResult.getStatusCode());
        assertTrue(actualGenericExceptionHandlerResult.getHeaders().isEmpty());
    }
}//package com.ss.eastcoderbank.userservice.exceptionHandler;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class AppExceptionHandlerTest {
//    @Test
//    public void testGenericExceptionHandler() {
//        AppExceptionHandler appExceptionHandler = new AppExceptionHandler();
//        ResponseEntity<String> actualGenericExceptionHandlerResult = appExceptionHandler
//                .genericExceptionHandler(new Exception("An error occurred"));
//        assertEquals("An error occurred", actualGenericExceptionHandlerResult.getBody());
//        assertEquals("<500 INTERNAL_SERVER_ERROR Internal Server Error,An error occurred,[]>",
//                actualGenericExceptionHandlerResult.toString());
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualGenericExceptionHandlerResult.getStatusCode());
//        assertTrue(actualGenericExceptionHandlerResult.getHeaders().isEmpty());
//    }
//
//    @Test
//    public void testGenericExceptionHandler2() {
//        AppExceptionHandler appExceptionHandler = new AppExceptionHandler();
//
//        Exception exception = new Exception();
//        exception.addSuppressed(new Throwable());
//        ResponseEntity<String> actualGenericExceptionHandlerResult = appExceptionHandler.genericExceptionHandler(exception);
//        assertNull(actualGenericExceptionHandlerResult.getBody());
//        assertEquals("<500 INTERNAL_SERVER_ERROR Internal Server Error,[]>",
//                actualGenericExceptionHandlerResult.toString());
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualGenericExceptionHandlerResult.getStatusCode());
//        assertTrue(actualGenericExceptionHandlerResult.getHeaders().isEmpty());
//    }
//}

