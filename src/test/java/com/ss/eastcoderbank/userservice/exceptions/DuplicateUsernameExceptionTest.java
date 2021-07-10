package com.ss.eastcoderbank.userservice.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class DuplicateUsernameExceptionTest {
    @Test
    public void testConstructor() {
        DuplicateUsernameException actualDuplicateUsernameException = new DuplicateUsernameException("An error occurred");
        assertNull(actualDuplicateUsernameException.getCause());
        assertEquals("com.ss.eastcoderbank.userservice.exceptions.DuplicateUsernameException: An error occurred",
                actualDuplicateUsernameException.toString());
        assertEquals(0, actualDuplicateUsernameException.getSuppressed().length);
        assertEquals("An error occurred", actualDuplicateUsernameException.getMessage());
        assertEquals("An error occurred", actualDuplicateUsernameException.getLocalizedMessage());
    }
}

