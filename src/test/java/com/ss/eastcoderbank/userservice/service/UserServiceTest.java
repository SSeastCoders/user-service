package com.ss.eastcoderbank.userservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.dto.UserDto;
import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import com.ss.eastcoderbank.userservice.repository.UserRoleRepository;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateEmailException;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateUsernameException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ModelMapper.class, UserService.class})
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testUserRegistration() throws DuplicateConstraintsException {
        when(this.modelMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new ConstraintViolationException("An error occurred", mock(SQLException.class), "Constraint Name"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("iloveyou");
        registrationDto.setEmail("jane.doe@example.org");
        registrationDto.setUsername("janedoe");
        assertThrows(ConstraintViolationException.class, () -> this.userService.userRegistration(registrationDto));
        verify(this.modelMapper).getConfiguration();
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
    }

    @Test
    public void testUserRegistration2() throws DuplicateConstraintsException {
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");
        Optional<UserRole> ofResult = Optional.<UserRole>of(userRole);
        when(this.userRoleRepository.findUserRoleByTitle(anyString())).thenReturn(ofResult);

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");

        User user = new User();
        user.setLastName("Doe");
        user.setCredential(credential);
        user.setEmail("jane.doe@example.org");
        user.setAddress(address);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setPhone("4105551212");
        user.setFirstName("Jane");
        user.setDataJoined(LocalDate.ofEpochDay(1L));
        user.setActiveStatus(true);
        user.setRole(userRole1);
        when(this.userRepository.save((User) any())).thenReturn(user);

        Credential credential1 = new Credential();
        credential1.setPassword("iloveyou");
        credential1.setUsername("janedoe");

        Address address1 = new Address();
        address1.setZip(0);
        address1.setCity("Oxford");
        address1.setStreetAddress("42 Main St");
        address1.setState("MD");

        UserRole userRole2 = new UserRole();
        userRole2.setUsers(new HashSet<User>());
        userRole2.setId(1);
        userRole2.setTitle("Dr");

        User user1 = new User();
        user1.setLastName("Doe");
        user1.setCredential(credential1);
        user1.setEmail("jane.doe@example.org");
        user1.setAddress(address1);
        user1.setDob(LocalDate.ofEpochDay(1L));
        user1.setId(1);
        user1.setPhone("4105551212");
        user1.setFirstName("Jane");
        user1.setDataJoined(LocalDate.ofEpochDay(1L));
        user1.setActiveStatus(true);
        user1.setRole(userRole2);
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(user1);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("iloveyou");
        registrationDto.setEmail("jane.doe@example.org");
        registrationDto.setUsername("janedoe");
        assertEquals(1, this.userService.userRegistration(registrationDto).intValue());
        verify(this.userRoleRepository).findUserRoleByTitle(anyString());
        verify(this.userRepository).save((User) any());
        verify(this.modelMapper, times(2)).getConfiguration();
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
        assertTrue(this.userService.getRoles().isEmpty());
        assertTrue(this.userService.getUsers().isEmpty());
    }

    @Test
    public void testUserRegistration3() throws DuplicateConstraintsException {
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");
        Optional<UserRole> ofResult = Optional.<UserRole>of(userRole);
        when(this.userRoleRepository.findUserRoleByTitle(anyString())).thenReturn(ofResult);
        when(this.userRepository.save((User) any())).thenThrow(new DataIntegrityViolationException("Msg"));

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");

        User user = new User();
        user.setLastName("Doe");
        user.setCredential(credential);
        user.setEmail("jane.doe@example.org");
        user.setAddress(address);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setPhone("4105551212");
        user.setFirstName("Jane");
        user.setDataJoined(LocalDate.ofEpochDay(1L));
        user.setActiveStatus(true);
        user.setRole(userRole1);
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(user);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("iloveyou");
        registrationDto.setEmail("jane.doe@example.org");
        registrationDto.setUsername("janedoe");
        assertThrows(DataIntegrityViolationException.class, () -> this.userService.userRegistration(registrationDto));
        verify(this.userRoleRepository).findUserRoleByTitle(anyString());
        verify(this.userRepository).save((User) any());
        verify(this.modelMapper, times(2)).getConfiguration();
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
    }

    @Test
    public void testGetUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        when(this.userRepository.findAll()).thenReturn(userList);
        List<User> actualUsers = this.userService.getUsers();
        assertSame(userList, actualUsers);
        assertTrue(actualUsers.isEmpty());
        verify(this.userRepository).findAll();
        assertTrue(this.userService.getRoles().isEmpty());
    }

    @Test
    public void testGetRoles() {
        ArrayList<UserRole> userRoleList = new ArrayList<UserRole>();
        when(this.userRoleRepository.findAll()).thenReturn(userRoleList);
        List<UserRole> actualRoles = this.userService.getRoles();
        assertSame(userRoleList, actualRoles);
        assertTrue(actualRoles.isEmpty());
        verify(this.userRoleRepository).findAll();
        assertTrue(this.userService.getUsers().isEmpty());
    }


    @Test
    public void testHandleUniqueConstraints2() throws DuplicateConstraintsException {
        assertThrows(DuplicateConstraintsException.class,
                () -> this.userService.handleUniqueConstraints("emailandusernameconstraint"));
    }

    @Test
    public void testHandleUniqueConstraints3() throws DuplicateConstraintsException {
        assertThrows(DuplicateEmailException.class, () -> this.userService.handleUniqueConstraints("emailconstraint"));
    }

    @Test
    public void testHandleUniqueConstraints4() throws DuplicateConstraintsException {
        assertThrows(DuplicateUsernameException.class,
                () -> this.userService.handleUniqueConstraints("usernameconstraint"));
    }
}

