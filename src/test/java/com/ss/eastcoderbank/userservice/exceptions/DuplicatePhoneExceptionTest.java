package com.ss.eastcoderbank.userservice.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class DuplicatePhoneExceptionTest {
    @Test
    public void testConstructor() {
        DuplicatePhoneException actualDuplicatePhoneException = new DuplicatePhoneException("An error occurred");
        assertNull(actualDuplicatePhoneException.getCause());
        assertEquals("com.ss.eastcoderbank.userservice.exceptions.DuplicatePhoneException: An error occurred",
                actualDuplicatePhoneException.toString());
        assertEquals(0, actualDuplicatePhoneException.getSuppressed().length);
        assertEquals("An error occurred", actualDuplicatePhoneException.getMessage());
        assertEquals("An error occurred", actualDuplicatePhoneException.getLocalizedMessage());
    }
}

