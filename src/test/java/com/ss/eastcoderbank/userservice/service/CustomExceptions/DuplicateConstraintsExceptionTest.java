package com.ss.eastcoderbank.userservice.service.CustomExceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class DuplicateConstraintsExceptionTest {
    @Test
    public void testConstructor() {
        DuplicateConstraintsException actualDuplicateConstraintsException = new DuplicateConstraintsException(
                "An error occurred");
        assertNull(actualDuplicateConstraintsException.getCause());
        assertEquals("com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException: An error"
                + " occurred", actualDuplicateConstraintsException.toString());
        assertEquals(0, actualDuplicateConstraintsException.getSuppressed().length);
        assertEquals("An error occurred", actualDuplicateConstraintsException.getMessage());
        assertEquals("An error occurred", actualDuplicateConstraintsException.getLocalizedMessage());
    }
}

