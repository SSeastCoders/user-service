package com.ss.eastcoderbank.userservice.service;

import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.dto.UserDto;
import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceDtoTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
    }


    @Test
    public void testConvertToEntity() {
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        UserDto userDto = new UserDto();
        userDto.setCredential(credential);
        userDto.setEmail("jane.doe@example.org");
        userDto.setAddress(address);
        userDto.setDob(LocalDate.ofEpochDay(1L));
        userDto.setId(1);
        userDto.setPhone("4105551212");
        userDto.setFirstName("Jane");
        userDto.setLastName("Doe");
        userDto.setDateJoined(LocalDate.ofEpochDay(1L));
        userDto.setActiveStatus(true);
        userDto.setRole(userRole);
        User actualConvertToEntityResult = this.userService.convertToEntity(userDto);
        assertEquals("User(id=1, role=UserRole(id=1, title=Dr, users=[]), firstName=Jane, lastName=Doe, dob=1970-01-02,"
                + " email=jane.doe@example.org, phone=4105551212, address=Address(streetAddress=42 Main St, city=Oxford,"
                + " zip=1, state=MD), dataJoined=null, activeStatus=true, credential=Credential(username=janedoe,"
                + " password=iloveyou))", actualConvertToEntityResult.toString());
        assertEquals("Doe", actualConvertToEntityResult.getLastName());
        assertEquals("4105551212", actualConvertToEntityResult.getPhone());
        assertEquals("jane.doe@example.org", actualConvertToEntityResult.getEmail());
        assertTrue(actualConvertToEntityResult.isActiveStatus());
        assertEquals("Jane", actualConvertToEntityResult.getFirstName());
        assertEquals(1, actualConvertToEntityResult.getId().intValue());
    }

    @Test
    public void testConvertToEntity2() {
        Credential credential = new Credential("janedoe", "iloveyou");
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        UserDto userDto = new UserDto();
        userDto.setCredential(credential);
        userDto.setEmail("jane.doe@example.org");
        userDto.setAddress(address);
        userDto.setDob(LocalDate.ofEpochDay(1L));
        userDto.setId(1);
        userDto.setPhone("4105551212");
        userDto.setFirstName("Jane");
        userDto.setLastName("Doe");
        userDto.setDateJoined(LocalDate.ofEpochDay(1L));
        userDto.setActiveStatus(true);
        userDto.setRole(userRole);
        User actualConvertToEntityResult = this.userService.convertToEntity(userDto);
        assertEquals("User(id=1, role=UserRole(id=1, title=Dr, users=[]), firstName=Jane, lastName=Doe, dob=1970-01-02,"
                + " email=jane.doe@example.org, phone=4105551212, address=Address(streetAddress=42 Main St, city=Oxford,"
                + " zip=1, state=MD), dataJoined=null, activeStatus=true, credential=Credential(username=janedoe,"
                + " password=iloveyou))", actualConvertToEntityResult.toString());
        assertEquals("Doe", actualConvertToEntityResult.getLastName());
        assertEquals("4105551212", actualConvertToEntityResult.getPhone());
        assertEquals("jane.doe@example.org", actualConvertToEntityResult.getEmail());
        assertTrue(actualConvertToEntityResult.isActiveStatus());
        assertEquals("Jane", actualConvertToEntityResult.getFirstName());
        assertEquals(1, actualConvertToEntityResult.getId().intValue());
    }

    @Test
    public void testConvertToEntity3() {
        Credential credential = new Credential();
        credential.setPassword("42");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        UserDto userDto = new UserDto();
        userDto.setCredential(credential);
        userDto.setEmail("jane.doe@example.org");
        userDto.setAddress(address);
        userDto.setDob(LocalDate.ofEpochDay(1L));
        userDto.setId(1);
        userDto.setPhone("4105551212");
        userDto.setFirstName("Jane");
        userDto.setLastName("Doe");
        userDto.setDateJoined(LocalDate.ofEpochDay(1L));
        userDto.setActiveStatus(true);
        userDto.setRole(userRole);
        User actualConvertToEntityResult = this.userService.convertToEntity(userDto);
        assertEquals("User(id=1, role=UserRole(id=1, title=Dr, users=[]), firstName=Jane, lastName=Doe, dob=1970-01-02,"
                + " email=jane.doe@example.org, phone=4105551212, address=Address(streetAddress=42 Main St, city=Oxford,"
                + " zip=1, state=MD), dataJoined=null, activeStatus=true, credential=Credential(username=janedoe,"
                + " password=42))", actualConvertToEntityResult.toString());
        assertEquals("Doe", actualConvertToEntityResult.getLastName());
        assertEquals("4105551212", actualConvertToEntityResult.getPhone());
        assertEquals("jane.doe@example.org", actualConvertToEntityResult.getEmail());
        assertTrue(actualConvertToEntityResult.isActiveStatus());
        assertEquals("Jane", actualConvertToEntityResult.getFirstName());
        assertEquals(1, actualConvertToEntityResult.getId().intValue());
    }

    @Test
    public void testConvertToEntity4() {
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        UserDto userDto = new UserDto();
        userDto.setCredential(credential);
        userDto.setEmail("jane.doe@example.org");
        userDto.setAddress(address);
        userDto.setDob(null);
        userDto.setId(1);
        userDto.setPhone("4105551212");
        userDto.setFirstName("Jane");
        userDto.setLastName("Doe");
        userDto.setDateJoined(LocalDate.ofEpochDay(1L));
        userDto.setActiveStatus(true);
        userDto.setRole(userRole);
        User actualConvertToEntityResult = this.userService.convertToEntity(userDto);
        assertEquals("User(id=1, role=UserRole(id=1, title=Dr, users=[]), firstName=Jane, lastName=Doe, dob=null,"
                + " email=jane.doe@example.org, phone=4105551212, address=Address(streetAddress=42 Main St, city=Oxford,"
                + " zip=1, state=MD), dataJoined=null, activeStatus=true, credential=Credential(username=janedoe,"
                + " password=iloveyou))", actualConvertToEntityResult.toString());
        assertNull(actualConvertToEntityResult.getDob());
        assertEquals("Doe", actualConvertToEntityResult.getLastName());
        assertEquals("4105551212", actualConvertToEntityResult.getPhone());
        assertEquals("jane.doe@example.org", actualConvertToEntityResult.getEmail());
        assertTrue(actualConvertToEntityResult.isActiveStatus());
        assertEquals("Jane", actualConvertToEntityResult.getFirstName());
        assertEquals(1, actualConvertToEntityResult.getId().intValue());
    }

    @Test
    public void testRegistrationToUser() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("iloveyou");
        registrationDto.setEmail("jane.doe@example.org");
        registrationDto.setUsername("janedoe");
        User actualRegistrationToUserResult = this.userService.registrationToUser(registrationDto);
        assertEquals(
                "User(id=null, role=null, firstName=null, lastName=null, dob=null, email=jane.doe@example.org, phone=null,"
                        + " address=null, dataJoined=null, activeStatus=false, credential=Credential(username=janedoe,"
                        + " password=iloveyou))",
                actualRegistrationToUserResult.toString());
        assertEquals("jane.doe@example.org", actualRegistrationToUserResult.getEmail());
        Credential credential = actualRegistrationToUserResult.getCredential();
        assertEquals("iloveyou", credential.getPassword());
        assertEquals("janedoe", credential.getUsername());
    }

    @Test
    public void testRegistrationToUser2() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("com.ss.eastcoderbank.userservice.dto.RegistrationDto");
        registrationDto.setEmail("jane.doe@example.org");
        registrationDto.setUsername("janedoe");
        User actualRegistrationToUserResult = this.userService.registrationToUser(registrationDto);
        assertEquals(
                "User(id=null, role=null, firstName=null, lastName=null, dob=null, email=jane.doe@example.org, phone=null,"
                        + " address=null, dataJoined=null, activeStatus=false, credential=Credential(username=janedoe,"
                        + " password=com.ss.eastcoderbank.userservice.dto.RegistrationDto))",
                actualRegistrationToUserResult.toString());
        assertEquals("jane.doe@example.org", actualRegistrationToUserResult.getEmail());
        Credential credential = actualRegistrationToUserResult.getCredential();
        assertEquals("com.ss.eastcoderbank.userservice.dto.RegistrationDto", credential.getPassword());
        assertEquals("janedoe", credential.getUsername());
    }

    @Test
    public void testRegistrationToUser3() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("com.ss.eastcoderbank.userservice.dto.RegistrationDto");
        registrationDto.setEmail("com.ss.eastcoderbank.userservice.dto.RegistrationDto");
        registrationDto.setUsername("janedoe");
        User actualRegistrationToUserResult = this.userService.registrationToUser(registrationDto);
        assertEquals(
                "User(id=null, role=null, firstName=null, lastName=null, dob=null, email=com.ss.eastcoderbank.userservice"
                        + ".dto.RegistrationDto, phone=null, address=null, dataJoined=null, activeStatus=false, credential"
                        + "=Credential(username=janedoe, password=com.ss.eastcoderbank.userservice.dto.RegistrationDto))",
                actualRegistrationToUserResult.toString());
        assertEquals("com.ss.eastcoderbank.userservice.dto.RegistrationDto", actualRegistrationToUserResult.getEmail());
        Credential credential = actualRegistrationToUserResult.getCredential();
        assertEquals("com.ss.eastcoderbank.userservice.dto.RegistrationDto", credential.getPassword());
        assertEquals("janedoe", credential.getUsername());
    }


    @Test
    public void testRegistrateDtoConversion() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("@gmail.com");
        registrationDto.setUsername("alejandro");
        registrationDto.setPassword("password");
        User user = ReflectionTestUtils.invokeMethod(userService, "registrationToUser", registrationDto);
        User expected = new User();
        expected.setEmail("@gmail.com");
        Credential credential = new Credential("alejandro", "password");
        expected.setCredential(credential);
        assertEquals(expected, user);

    }

}
