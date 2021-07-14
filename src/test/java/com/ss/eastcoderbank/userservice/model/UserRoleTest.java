package com.ss.eastcoderbank.userservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UserRoleTest {
    @Test
    public void testConstructor() {
        UserRole actualUserRole = new UserRole("Dr");
        assertNull(actualUserRole.getId());
        assertEquals("UserRole(id=null, title=Dr, users=[])", actualUserRole.toString());
        assertTrue(actualUserRole.getUsers().isEmpty());
        assertEquals("Dr", actualUserRole.getTitle());
    }
}

