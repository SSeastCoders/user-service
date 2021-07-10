package com.ss.eastcoderbank.userservice.service;

import com.ss.eastcoderbank.userservice.dto.UserDTO;
import com.ss.eastcoderbank.userservice.exceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.userservice.exceptions.DuplicateEmailException;
import com.ss.eastcoderbank.userservice.exceptions.DuplicatePhoneException;
import com.ss.eastcoderbank.userservice.exceptions.DuplicateUsernameException;
import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import com.ss.eastcoderbank.userservice.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ModelMapper.class, UserService.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testManuallyCreateUser()
            throws DuplicateEmailException, DuplicatePhoneException, DuplicateUsernameException {
        when(this.modelMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new DuplicateConstraintsException("An error occurred"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

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

        UserDTO userDTO = new UserDTO();
        userDTO.setLastName("Doe");
        userDTO.setCredential(credential);
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setAddress(address);
        userDTO.setDob(LocalDate.ofEpochDay(1L));
        userDTO.setId(1);
        userDTO.setPhone("4105551212");
        userDTO.setFirstName("Jane");
        userDTO.setDateJoined(LocalDate.ofEpochDay(1L));
        userDTO.setRole(userRole);
        userDTO.setActiveStatus(true);
        assertThrows(DuplicateConstraintsException.class, () -> this.userService.manuallyCreateUser(userDTO));
        verify(this.modelMapper).getConfiguration();
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
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
        when(this.userRepository.saveAndFlush((User) any())).thenReturn(user);

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
        when(this.bCryptPasswordEncoder.encode((CharSequence) any())).thenReturn("foo");

        Credential credential2 = new Credential();
        credential2.setPassword("iloveyou");
        credential2.setUsername("janedoe");

        Address address2 = new Address();
        address2.setZip(1);
        address2.setCity("Oxford");
        address2.setStreetAddress("42 Main St");
        address2.setState("MD");

        UserRole userRole3 = new UserRole();
        userRole3.setUsers(new HashSet<User>());
        userRole3.setId(1);
        userRole3.setTitle("Dr");

        UserDTO userDTO = new UserDTO();
        userDTO.setLastName("Doe");
        userDTO.setCredential(credential2);
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setAddress(address2);
        userDTO.setDob(LocalDate.ofEpochDay(1L));
        userDTO.setId(1);
        userDTO.setPhone("4105551212");
        userDTO.setFirstName("Jane");
        userDTO.setDateJoined(LocalDate.ofEpochDay(1L));
        userDTO.setRole(userRole3);
        userDTO.setActiveStatus(true);
        assertEquals(1, this.userService.manuallyCreateUser(userDTO).intValue());
        verify(this.userRoleRepository).findUserRoleByTitle(anyString());
        verify(this.userRepository).saveAndFlush((User) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
        verify(this.bCryptPasswordEncoder).encode((CharSequence) any());
    }

    @Test
    public void testManuallyCreateUser3()
            throws DuplicateEmailException, DuplicatePhoneException, DuplicateUsernameException {
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");
        Optional<UserRole> ofResult = Optional.<UserRole>of(userRole);
        when(this.userRoleRepository.findUserRoleByTitle(anyString())).thenReturn(ofResult);
        when(this.userRepository.saveAndFlush((User) any())).thenThrow(new DataIntegrityViolationException("Msg"));

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
        when(this.bCryptPasswordEncoder.encode((CharSequence) any())).thenReturn("foo");

        Credential credential1 = new Credential();
        credential1.setPassword("iloveyou");
        credential1.setUsername("janedoe");

        Address address1 = new Address();
        address1.setZip(1);
        address1.setCity("Oxford");
        address1.setStreetAddress("42 Main St");
        address1.setState("MD");

        UserRole userRole2 = new UserRole();
        userRole2.setUsers(new HashSet<User>());
        userRole2.setId(1);
        userRole2.setTitle("Dr");

        UserDTO userDTO = new UserDTO();
        userDTO.setLastName("Doe");
        userDTO.setCredential(credential1);
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setAddress(address1);
        userDTO.setDob(LocalDate.ofEpochDay(1L));
        userDTO.setId(1);
        userDTO.setPhone("4105551212");
        userDTO.setFirstName("Jane");
        userDTO.setDateJoined(LocalDate.ofEpochDay(1L));
        userDTO.setRole(userRole2);
        userDTO.setActiveStatus(true);
        assertThrows(DataIntegrityViolationException.class, () -> this.userService.manuallyCreateUser(userDTO));
        verify(this.userRoleRepository).findUserRoleByTitle(anyString());
        verify(this.userRepository).saveAndFlush((User) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
        verify(this.bCryptPasswordEncoder).encode((CharSequence) any());
    }

    @Test
    public void testUserDTOToUser() {
        when(this.modelMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new DuplicateConstraintsException("An error occurred"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

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

        UserDTO userDTO = new UserDTO();
        userDTO.setLastName("Doe");
        userDTO.setCredential(credential);
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setAddress(address);
        userDTO.setDob(LocalDate.ofEpochDay(1L));
        userDTO.setId(1);
        userDTO.setPhone("4105551212");
        userDTO.setFirstName("Jane");
        userDTO.setDateJoined(LocalDate.ofEpochDay(1L));
        userDTO.setRole(userRole);
        userDTO.setActiveStatus(true);
        assertThrows(DuplicateConstraintsException.class, () -> this.userService.userDTOToUser(userDTO));
        verify(this.modelMapper).getConfiguration();
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
    }

    @Test
    public void testUserDTOToUser2() {
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

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
        user.setRole(userRole);
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(user);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Credential credential1 = new Credential();
        credential1.setPassword("iloveyou");
        credential1.setUsername("janedoe");

        Address address1 = new Address();
        address1.setZip(1);
        address1.setCity("Oxford");
        address1.setStreetAddress("42 Main St");
        address1.setState("MD");

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");

        UserDTO userDTO = new UserDTO();
        userDTO.setLastName("Doe");
        userDTO.setCredential(credential1);
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setAddress(address1);
        userDTO.setDob(LocalDate.ofEpochDay(1L));
        userDTO.setId(1);
        userDTO.setPhone("4105551212");
        userDTO.setFirstName("Jane");
        userDTO.setDateJoined(LocalDate.ofEpochDay(1L));
        userDTO.setRole(userRole1);
        userDTO.setActiveStatus(true);
        assertSame(user, this.userService.userDTOToUser(userDTO));
        verify(this.modelMapper).getConfiguration();
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
    }
}

