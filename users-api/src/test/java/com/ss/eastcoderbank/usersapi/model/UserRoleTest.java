package com.ss.eastcoderbank.usersapi.model;

import com.ss.eastcoderbank.core.model.user.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

