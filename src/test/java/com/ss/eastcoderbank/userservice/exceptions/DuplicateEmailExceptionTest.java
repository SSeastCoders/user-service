package com.ss.eastcoderbank.userservice.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DuplicateEmailExceptionTest {
    @Test
    public void testConstructor() {
        DuplicateEmailException actualDuplicateEmailException = new DuplicateEmailException("An error occurred");
        assertNull(actualDuplicateEmailException.getCause());
        assertEquals("com.ss.eastcoderbank.userservice.exceptions.DuplicateEmailException: An error occurred",
                actualDuplicateEmailException.toString());
        assertEquals(0, actualDuplicateEmailException.getSuppressed().length);
        assertEquals("An error occurred", actualDuplicateEmailException.getMessage());
        assertEquals("An error occurred", actualDuplicateEmailException.getLocalizedMessage());
    }
}

