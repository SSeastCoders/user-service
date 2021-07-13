package com.ss.eastcoderbank.userservice.service.CustomExceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class DuplicateUsernameExceptionTest {
    @Test
    public void testConstructor() {
        DuplicateUsernameException actualDuplicateUsernameException = new DuplicateUsernameException();
        assertNull(actualDuplicateUsernameException.getCause());
        assertEquals(
                "com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateUsernameException: duplicate" + " username",
                actualDuplicateUsernameException.toString());
        assertEquals(0, actualDuplicateUsernameException.getSuppressed().length);
        assertEquals(ExceptionMessages.USERNAME, actualDuplicateUsernameException.getMessage());
        assertEquals(ExceptionMessages.USERNAME, actualDuplicateUsernameException.getLocalizedMessage());
    }

    @Test
    public void testConstructor2() {
        DuplicateUsernameException actualDuplicateUsernameException = new DuplicateUsernameException("An error occurred");
        assertNull(actualDuplicateUsernameException.getCause());
        assertEquals(
                "com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateUsernameException: An error" + " occurred",
                actualDuplicateUsernameException.toString());
        assertEquals(0, actualDuplicateUsernameException.getSuppressed().length);
        assertEquals("An error occurred", actualDuplicateUsernameException.getMessage());
        assertEquals("An error occurred", actualDuplicateUsernameException.getLocalizedMessage());
    }
}

