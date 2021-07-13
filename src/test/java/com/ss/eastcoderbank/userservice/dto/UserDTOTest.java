package com.ss.eastcoderbank.userservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class UserDTOTest {
    @Test
    public void testConstructor() {
        UserDTO actualUserDTO = new UserDTO();
        actualUserDTO.setActiveStatus(true);
        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");
        actualUserDTO.setAddress(address);
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");
        actualUserDTO.setCredential(credential);
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        actualUserDTO.setDateJoined(ofEpochDayResult);
        LocalDate ofEpochDayResult1 = LocalDate.ofEpochDay(1L);
        actualUserDTO.setDob(ofEpochDayResult1);
        actualUserDTO.setEmail("jane.doe@example.org");
        actualUserDTO.setFirstName("Jane");
        actualUserDTO.setId(1);
        actualUserDTO.setLastName("Doe");
        actualUserDTO.setPhone("4105551212");
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");
        actualUserDTO.setRole(userRole);
        assertSame(address, actualUserDTO.getAddress());
        assertSame(credential, actualUserDTO.getCredential());
        LocalDate dateJoined = actualUserDTO.getDateJoined();
        assertSame(ofEpochDayResult, dateJoined);
        LocalDate dob = actualUserDTO.getDob();
        assertEquals(dob, dateJoined);
        assertSame(ofEpochDayResult1, dob);
        assertEquals(ofEpochDayResult, dob);
        assertEquals("jane.doe@example.org", actualUserDTO.getEmail());
        assertEquals("Jane", actualUserDTO.getFirstName());
        assertEquals(1, actualUserDTO.getId().intValue());
        assertEquals("Doe", actualUserDTO.getLastName());
        assertEquals("4105551212", actualUserDTO.getPhone());
        assertSame(userRole, actualUserDTO.getRole());
        assertTrue(actualUserDTO.isActiveStatus());
        assertEquals(
                "UserDTO{id=1, role=UserRole(id=1, title=Dr, users=[]), firstName='Jane', lastName='Doe', dob=1970-01-02,"
                        + " email='jane.doe@example.org', phone='4105551212', address=Address(streetAddress=42 Main St, city=Oxford,"
                        + " zip=1, state=MD), dateJoined=1970-01-02, activeStatus=true, credential=Credential(username=janedoe,"
                        + " password=iloveyou)}",
                actualUserDTO.toString());
    }
}

