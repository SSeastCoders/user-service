package com.ss.eastcoderbank.userservice.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void testConstructor() {
        User actualUser = new User();
        actualUser.setActiveStatus(true);
        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");
        actualUser.setAddress(address);
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");
        actualUser.setCredential(credential);
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        actualUser.setDateJoined(ofEpochDayResult);
        LocalDate ofEpochDayResult1 = LocalDate.ofEpochDay(1L);
        actualUser.setDob(ofEpochDayResult1);
        actualUser.setEmail("jane.doe@example.org");
        actualUser.setFirstName("Jane");
        actualUser.setId(1);
        actualUser.setLastName("Doe");
        actualUser.setPhone("4105551212");
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");
        actualUser.setRole(userRole);
        assertSame(address, actualUser.getAddress());
        assertSame(credential, actualUser.getCredential());
        LocalDate dataJoined = actualUser.getDateJoined();
        assertSame(ofEpochDayResult, dataJoined);
        LocalDate dob = actualUser.getDob();
        assertEquals(dob, dataJoined);
        assertSame(ofEpochDayResult1, dob);
        assertEquals(ofEpochDayResult, dob);
        assertEquals("jane.doe@example.org", actualUser.getEmail());
        assertEquals("Jane", actualUser.getFirstName());
        assertEquals(1, actualUser.getId().intValue());
        assertEquals("Doe", actualUser.getLastName());
        assertEquals("4105551212", actualUser.getPhone());
        assertSame(userRole, actualUser.getRole());
        assertTrue(actualUser.isActiveStatus());

    }
}

