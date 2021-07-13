package com.ss.eastcoderbank.userservice.service.CustomExceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class DuplicateEmailExceptionTest {
    @Test
    public void testConstructor() {
        DuplicateEmailException actualDuplicateEmailException = new DuplicateEmailException();
        assertNull(actualDuplicateEmailException.getCause());
        assertEquals("com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateEmailException: duplicate email",
                actualDuplicateEmailException.toString());
        assertEquals(0, actualDuplicateEmailException.getSuppressed().length);
        assertEquals(ExceptionMessages.EMAIL, actualDuplicateEmailException.getMessage());
        assertEquals(ExceptionMessages.EMAIL, actualDuplicateEmailException.getLocalizedMessage());
    }

    @Test
    public void testConstructor2() {
        DuplicateEmailException actualDuplicateEmailException = new DuplicateEmailException("An error occurred");
        assertNull(actualDuplicateEmailException.getCause());
        assertEquals("com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateEmailException: An error occurred",
                actualDuplicateEmailException.toString());
        assertEquals(0, actualDuplicateEmailException.getSuppressed().length);
        assertEquals("An error occurred", actualDuplicateEmailException.getMessage());
        assertEquals("An error occurred", actualDuplicateEmailException.getLocalizedMessage());
    }
}

