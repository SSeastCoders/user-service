package com.ss.eastcoderbank.userservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ss.eastcoderbank.userservice.exceptions.DuplicateEmailException;
import com.ss.eastcoderbank.userservice.exceptions.DuplicatePhoneException;
import com.ss.eastcoderbank.userservice.exceptions.DuplicateUsernameException;
import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import com.ss.eastcoderbank.userservice.repository.UserRoleRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testManuallyCreateUser()
            throws DuplicateEmailException, DuplicatePhoneException, DuplicateUsernameException {
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
        Optional<User> ofResult1 = Optional.<User>of(user);
        when(this.userRepository.findByEmail(anyString())).thenReturn(ofResult1);
        assertThrows(DuplicateEmailException.class, () -> this.userService.manuallyCreateUser(new User()));
        verify(this.userRoleRepository).findUserRoleByTitle(anyString());
        verify(this.userRepository).findByEmail(anyString());
    }

    @Test
    public void testManuallyCreateUser2()
            throws DuplicateEmailException, DuplicatePhoneException, DuplicateUsernameException {
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
        Optional<User> ofResult1 = Optional.<User>of(user);
        when(this.userRepository.findByUsername(anyString())).thenReturn(ofResult1);
        when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.<User>empty());

        Credential credential1 = new Credential();
        credential1.setPassword("iloveyou");
        credential1.setUsername("janedoe");

        User user1 = new User();
        user1.setCredential(credential1);
        assertThrows(DuplicateUsernameException.class, () -> this.userService.manuallyCreateUser(user1));
        verify(this.userRoleRepository).findUserRoleByTitle(anyString());
        verify(this.userRepository).findByEmail(anyString());
        verify(this.userRepository).findByUsername(anyString());
    }
}

