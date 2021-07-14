package com.ss.eastcoderbank.userservice.dto;

import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {
    @Test
    public void testConstructor() {
        UserDto actualUserDto = new UserDto();
        actualUserDto.setActiveStatus(true);
        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");
        actualUserDto.setAddress(address);
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");
        actualUserDto.setCredential(credential);
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        actualUserDto.setDateJoined(ofEpochDayResult);
        LocalDate ofEpochDayResult1 = LocalDate.ofEpochDay(1L);
        actualUserDto.setDob(ofEpochDayResult1);
        actualUserDto.setEmail("jane.doe@example.org");
        actualUserDto.setFirstName("Jane");
        actualUserDto.setId(1);
        actualUserDto.setLastName("Doe");
        actualUserDto.setPhone("4105551212");
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");
        actualUserDto.setRole(userRole);
        assertSame(address, actualUserDto.getAddress());
        assertSame(credential, actualUserDto.getCredential());
        LocalDate dateJoined = actualUserDto.getDateJoined();
        assertSame(ofEpochDayResult, dateJoined);
        LocalDate dob = actualUserDto.getDob();
        assertEquals(dob, dateJoined);
        assertSame(ofEpochDayResult1, dob);
        assertEquals(ofEpochDayResult, dob);
        assertEquals("jane.doe@example.org", actualUserDto.getEmail());
        assertEquals("Jane", actualUserDto.getFirstName());
        assertEquals(1, actualUserDto.getId().intValue());
        assertEquals("Doe", actualUserDto.getLastName());
        assertEquals("4105551212", actualUserDto.getPhone());
        assertSame(userRole, actualUserDto.getRole());
        assertTrue(actualUserDto.isActiveStatus());
        assertEquals(
                "UserDTO{id=1, role=UserRole(id=1, title=Dr, users=[]), firstName='Jane', lastName='Doe', dob=1970-01-02,"
                        + " email='jane.doe@example.org', phone='4105551212', address=Address(streetAddress=42 Main St, city=Oxford,"
                        + " zip=1, state=MD), dateJoined=1970-01-02, activeStatus=true, credential=Credential(username=janedoe,"
                        + " password=iloveyou)}",
                actualUserDto.toString());
    }
}

