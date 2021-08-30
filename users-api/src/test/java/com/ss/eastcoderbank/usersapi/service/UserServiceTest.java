package com.ss.eastcoderbank.usersapi.service;

import com.ss.eastcoderbank.core.exeception.UserNotFoundException;
import com.ss.eastcoderbank.core.model.user.Address;
import com.ss.eastcoderbank.core.model.user.Credential;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.model.user.UserRole;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.core.repository.UserRoleRepository;
import com.ss.eastcoderbank.core.transferdto.UserDto;
import com.ss.eastcoderbank.core.transfermapper.UserMapper;
import com.ss.eastcoderbank.usersapi.dto.CreateUserDto;
import com.ss.eastcoderbank.usersapi.mapper.CreateUserMapper;
import com.ss.eastcoderbank.usersapi.mapper.UpdateProfileMapper;
import com.ss.eastcoderbank.usersapi.service.CustomExceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.usersapi.service.CustomExceptions.DuplicateEmailException;
import com.ss.eastcoderbank.usersapi.service.CustomExceptions.DuplicateUsernameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @MockBean
    private CreateUserMapper createUserMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UpdateProfileMapper updateProfileMapper;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testGetUsersByRole() {
        // Arrange
        when(this.userRepository.findUserByRoleTitle(anyString(), (org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<User>(new ArrayList<User>()));
        String title = "Dr";
        QPageRequest page = null;

        // Act
        Page<UserDto> actualUsersByRole = this.userService.getUsersByRole(title, page);

        // Assert
        assertTrue(actualUsersByRole.toList().isEmpty());
        verify(this.userRepository).findUserByRoleTitle(anyString(), (org.springframework.data.domain.Pageable) any());
        assertTrue(this.userService.getRoles().isEmpty());
    }

    @Test
    public void testGetUsersByRole2() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user);
        PageImpl<User> pageImpl = new PageImpl<User>(userList);
        when(this.userRepository.findUserByRoleTitle(anyString(), (org.springframework.data.domain.Pageable) any()))
                .thenReturn(pageImpl);

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");

        Address address1 = new Address();
        address1.setZip(1);
        address1.setCity("Oxford");
        address1.setStreetAddress("42 Main St");
        address1.setState("MD");

        UserDto userDto = new UserDto();
        userDto.setLastName("Doe");
        userDto.setEmail("jane.doe@example.org");
        userDto.setRole(userRole1);
        userDto.setDob(LocalDate.ofEpochDay(1L));
        userDto.setUsername("janedoe");
        userDto.setId(1);
        userDto.setActiveStatus(true);
        userDto.setPhone("4105551212");
        userDto.setFirstName("Jane");
        userDto.setDateJoined(LocalDate.ofEpochDay(1L));
        userDto.setAddress(address1);
        when(this.userMapper.mapToDto((User) any())).thenReturn(userDto);
        String title = "Dr";
        QPageRequest page = null;

        // Act
        Page<UserDto> actualUsersByRole = this.userService.getUsersByRole(title, page);

        // Assert
        assertEquals(1, actualUsersByRole.toList().size());
        verify(this.userRepository).findUserByRoleTitle(anyString(), (org.springframework.data.domain.Pageable) any());
        verify(this.userMapper).mapToDto((User) any());
        assertTrue(this.userService.getRoles().isEmpty());
    }

    @Test
    public void testGetUsersByRole3() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");

        Credential credential1 = new Credential();
        credential1.setPassword("iloveyou");
        credential1.setUsername("janedoe");

        Address address1 = new Address();
        address1.setZip(0);
        address1.setCity("Oxford");
        address1.setStreetAddress("42 Main St");
        address1.setState("MD");

        User user1 = new User();
        user1.setLastName("Doe");
        user1.setEmail("jane.doe@example.org");
        user1.setRole(userRole1);
        user1.setDob(LocalDate.ofEpochDay(1L));
        user1.setId(1);
        user1.setActiveStatus(true);
        user1.setPhone("4105551212");
        user1.setCredential(credential1);
        user1.setFirstName("Jane");
        user1.setDateJoined(LocalDate.ofEpochDay(1L));
        user1.setAddress(address1);

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user);
        PageImpl<User> pageImpl = new PageImpl<User>(userList);
        when(this.userRepository.findUserByRoleTitle(anyString(), (org.springframework.data.domain.Pageable) any()))
                .thenReturn(pageImpl);

        UserRole userRole2 = new UserRole();
        userRole2.setUsers(new HashSet<User>());
        userRole2.setId(1);
        userRole2.setTitle("Dr");

        Address address2 = new Address();
        address2.setZip(1);
        address2.setCity("Oxford");
        address2.setStreetAddress("42 Main St");
        address2.setState("MD");

        UserDto userDto = new UserDto();
        userDto.setLastName("Doe");
        userDto.setEmail("jane.doe@example.org");
        userDto.setRole(userRole2);
        userDto.setDob(LocalDate.ofEpochDay(1L));
        userDto.setUsername("janedoe");
        userDto.setId(1);
        userDto.setActiveStatus(true);
        userDto.setPhone("4105551212");
        userDto.setFirstName("Jane");
        userDto.setDateJoined(LocalDate.ofEpochDay(1L));
        userDto.setAddress(address2);
        when(this.userMapper.mapToDto((User) any())).thenReturn(userDto);
        String title = "Dr";
        QPageRequest page = null;

        // Act
        Page<UserDto> actualUsersByRole = this.userService.getUsersByRole(title, page);

        // Assert
        assertEquals(2, actualUsersByRole.toList().size());
        verify(this.userRepository).findUserByRoleTitle(anyString(), (org.springframework.data.domain.Pageable) any());
        verify(this.userMapper, times(2)).mapToDto((User) any());
        assertTrue(this.userService.getRoles().isEmpty());
    }

    @Test
    public void testCreateUser() throws DuplicateConstraintsException {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");
        Optional<UserRole> ofResult = Optional.<UserRole>of(userRole);

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");
        when(this.userRoleRepository.getById((Integer) any())).thenReturn(userRole1);
        when(this.userRoleRepository.findUserRoleByTitle(anyString())).thenReturn(ofResult);

        UserRole userRole2 = new UserRole();
        userRole2.setUsers(new HashSet<User>());
        userRole2.setId(1);
        userRole2.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole2);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("foo");

        UserRole userRole3 = new UserRole();
        userRole3.setUsers(new HashSet<User>());
        userRole3.setId(1);
        userRole3.setTitle("Dr");

        Credential credential1 = new Credential();
        credential1.setPassword("iloveyou");
        credential1.setUsername("janedoe");

        Address address1 = new Address();
        address1.setZip(1);
        address1.setCity("Oxford");
        address1.setStreetAddress("42 Main St");
        address1.setState("MD");

        User user1 = new User();
        user1.setLastName("Doe");
        user1.setEmail("jane.doe@example.org");
        user1.setRole(userRole3);
        user1.setDob(LocalDate.ofEpochDay(1L));
        user1.setId(1);
        user1.setActiveStatus(true);
        user1.setPhone("4105551212");
        user1.setCredential(credential1);
        user1.setFirstName("Jane");
        user1.setDateJoined(LocalDate.ofEpochDay(1L));
        user1.setAddress(address1);
        when(this.createUserMapper.mapToEntity((CreateUserDto) any())).thenReturn(user1);
        CreateUserDto createUserDto = new CreateUserDto("janedoe", "iloveyou", "jane.doe@example.org");

        // Act
        Integer actualCreateUserResult = this.userService.createUser(createUserDto);

        // Assert
        assertEquals(1, actualCreateUserResult.intValue());
        verify(this.userRoleRepository).findUserRoleByTitle((String) any());
        verify(this.userRoleRepository).getById((Integer) any());
        verify(this.userRepository).save((User) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
        verify(this.createUserMapper).mapToEntity((CreateUserDto) any());
        assertEquals("foo", createUserDto.getPassword());
        assertTrue(this.userService.getRoles().isEmpty());
    }

    @Test
    public void testCreateUser2() throws DuplicateConstraintsException {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");
        Optional<UserRole> ofResult = Optional.<UserRole>of(userRole);

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");
        when(this.userRoleRepository.getById((Integer) any())).thenReturn(userRole1);
        when(this.userRoleRepository.findUserRoleByTitle(anyString())).thenReturn(ofResult);
        when(this.userRepository.save((User) any())).thenThrow(new DataIntegrityViolationException("Msg"));
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("foo");

        UserRole userRole2 = new UserRole();
        userRole2.setUsers(new HashSet<User>());
        userRole2.setId(1);
        userRole2.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole2);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);
        when(this.createUserMapper.mapToEntity((CreateUserDto) any())).thenReturn(user);
        CreateUserDto createUserDto = new CreateUserDto("janedoe", "iloveyou", "jane.doe@example.org");

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> this.userService.createUser(createUserDto));
        verify(this.userRoleRepository).findUserRoleByTitle((String) any());
        verify(this.userRoleRepository).getById((Integer) any());
        verify(this.userRepository).save((User) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
        verify(this.createUserMapper).mapToEntity((CreateUserDto) any());
    }



    @Test
    public void testGetUsers() {
        // Arrange
        when(this.userRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<User>(new ArrayList<User>()));
        int pageNumber = 10;
        int pageSize = 3;
        boolean asc = true;
        String sort = "username";


        // Act
        Page<UserDto> actualUsers = this.userService.getUsers(pageNumber, pageSize, true, "username");

        // Assert
        assertTrue(actualUsers.toList().isEmpty());
        verify(this.userRepository).findAll((org.springframework.data.domain.Pageable) any());
        assertTrue(this.userService.getRoles().isEmpty());
    }

    @Test
    public void testGetUsers2() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user);
        PageImpl<User> pageImpl = new PageImpl<User>(userList);
        when(this.userRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");

        Address address1 = new Address();
        address1.setZip(1);
        address1.setCity("Oxford");
        address1.setStreetAddress("42 Main St");
        address1.setState("MD");

        UserDto userDto = new UserDto();
        userDto.setLastName("Doe");
        userDto.setEmail("jane.doe@example.org");
        userDto.setRole(userRole1);
        userDto.setDob(LocalDate.ofEpochDay(1L));
        userDto.setUsername("janedoe");
        userDto.setId(1);
        userDto.setActiveStatus(true);
        userDto.setPhone("4105551212");
        userDto.setFirstName("Jane");
        userDto.setDateJoined(LocalDate.ofEpochDay(1L));
        userDto.setAddress(address1);
        when(this.userMapper.mapToDto((User) any())).thenReturn(userDto);
        int pageNumber = 10;
        int pageSize = 3;
        boolean asc = true;
        String sort = "username";
        // Act
        Page<UserDto> actualUsers = this.userService.getUsers(pageNumber, pageSize, true, "username");

        // Assert
        assertEquals(1, actualUsers.toList().size());
        verify(this.userRepository).findAll((org.springframework.data.domain.Pageable) any());
        verify(this.userMapper).mapToDto((User) any());
        assertTrue(this.userService.getRoles().isEmpty());
    }

    @Test
    public void testGetUsers3() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");

        Credential credential1 = new Credential();
        credential1.setPassword("iloveyou");
        credential1.setUsername("janedoe");

        Address address1 = new Address();
        address1.setZip(0);
        address1.setCity("Oxford");
        address1.setStreetAddress("42 Main St");
        address1.setState("MD");

        User user1 = new User();
        user1.setLastName("Doe");
        user1.setEmail("jane.doe@example.org");
        user1.setRole(userRole1);
        user1.setDob(LocalDate.ofEpochDay(1L));
        user1.setId(1);
        user1.setActiveStatus(true);
        user1.setPhone("4105551212");
        user1.setCredential(credential1);
        user1.setFirstName("Jane");
        user1.setDateJoined(LocalDate.ofEpochDay(1L));
        user1.setAddress(address1);

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user);
        PageImpl<User> pageImpl = new PageImpl<User>(userList);
        when(this.userRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);

        UserRole userRole2 = new UserRole();
        userRole2.setUsers(new HashSet<User>());
        userRole2.setId(1);
        userRole2.setTitle("Dr");

        Address address2 = new Address();
        address2.setZip(1);
        address2.setCity("Oxford");
        address2.setStreetAddress("42 Main St");
        address2.setState("MD");

        UserDto userDto = new UserDto();
        userDto.setLastName("Doe");
        userDto.setEmail("jane.doe@example.org");
        userDto.setRole(userRole2);
        userDto.setDob(LocalDate.ofEpochDay(1L));
        userDto.setUsername("janedoe");
        userDto.setId(1);
        userDto.setActiveStatus(true);
        userDto.setPhone("4105551212");
        userDto.setFirstName("Jane");
        userDto.setDateJoined(LocalDate.ofEpochDay(1L));
        userDto.setAddress(address2);
        when(this.userMapper.mapToDto((User) any())).thenReturn(userDto);
        int pageNumber = 10;
        int pageSize = 3;
        boolean asc = true;
        String sort = "username";
        // Act
        Page<UserDto> actualUsers = this.userService.getUsers(pageNumber, pageSize, true, "username");


        // Assert
        assertEquals(2, actualUsers.toList().size());
        verify(this.userRepository).findAll((org.springframework.data.domain.Pageable) any());
        verify(this.userMapper, times(2)).mapToDto((User) any());
        assertTrue(this.userService.getRoles().isEmpty());
    }

    @Test
    public void testGetUserById() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);
        Optional<User> ofResult = Optional.<User>of(user);
        when(this.userRepository.findById((Integer) any())).thenReturn(ofResult);

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");

        Address address1 = new Address();
        address1.setZip(1);
        address1.setCity("Oxford");
        address1.setStreetAddress("42 Main St");
        address1.setState("MD");

        UserDto userDto = new UserDto();
        userDto.setLastName("Doe");
        userDto.setEmail("jane.doe@example.org");
        userDto.setRole(userRole1);
        userDto.setDob(LocalDate.ofEpochDay(1L));
        userDto.setUsername("janedoe");
        userDto.setId(1);
        userDto.setActiveStatus(true);
        userDto.setPhone("4105551212");
        userDto.setFirstName("Jane");
        userDto.setDateJoined(LocalDate.ofEpochDay(1L));
        userDto.setAddress(address1);
        when(this.userMapper.mapToDto((User) any())).thenReturn(userDto);
        int id = 1;

        // Act
        UserDto actualUserById = this.userService.getUserById(id);

        // Assert
        assertSame(userDto, actualUserById);
        verify(this.userRepository).findById((Integer) any());
        verify(this.userMapper).mapToDto((User) any());
        assertTrue(this.userService.getRoles().isEmpty());
    }

    @Test
    public void testGetRoles() {
        // Arrange
        ArrayList<UserRole> userRoleList = new ArrayList<UserRole>();
        when(this.userRoleRepository.findAll()).thenReturn(userRoleList);

        // Act
        List<UserRole> actualRoles = this.userService.getRoles();

        // Assert
        assertSame(userRoleList, actualRoles);
        assertTrue(actualRoles.isEmpty());
        verify(this.userRoleRepository).findAll();
    }

    @Test
    public void testHandleUniqueConstraints() {
        // Arrange
        String constraint = "Constraint";

        // Act
        this.userService.handleUniqueConstraints(constraint);

        // Assert that nothing has changed
        assertTrue(this.userService.getRoles().isEmpty());
    }

    @Test
    public void testHandleUniqueConstraints2() {
        // Arrange
        String constraint = "emailandusernameconstraint";

        // Act and Assert
        assertThrows(DuplicateConstraintsException.class, () -> this.userService.handleUniqueConstraints(constraint));
    }

    @Test
    public void testHandleUniqueConstraints3() {
        // Arrange
        String constraint = "emailconstraint";

        // Act and Assert
        assertThrows(DuplicateEmailException.class, () -> this.userService.handleUniqueConstraints(constraint));
    }

    @Test
    public void testHandleUniqueConstraints4() {
        // Arrange
        String constraint = "usernameconstraint";

        // Act and Assert
        assertThrows(DuplicateUsernameException.class, () -> this.userService.handleUniqueConstraints(constraint));
    }

    @Test
    public void testDeactivateUser() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);
        Optional<User> ofResult = Optional.<User>of(user);

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");

        Credential credential1 = new Credential();
        credential1.setPassword("iloveyou");
        credential1.setUsername("janedoe");

        Address address1 = new Address();
        address1.setZip(1);
        address1.setCity("Oxford");
        address1.setStreetAddress("42 Main St");
        address1.setState("MD");

        User user1 = new User();
        user1.setLastName("Doe");
        user1.setEmail("jane.doe@example.org");
        user1.setRole(userRole1);
        user1.setDob(LocalDate.ofEpochDay(1L));
        user1.setId(1);
        user1.setActiveStatus(true);
        user1.setPhone("4105551212");
        user1.setCredential(credential1);
        user1.setFirstName("Jane");
        user1.setDateJoined(LocalDate.ofEpochDay(1L));
        user1.setAddress(address1);
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findById((Integer) any())).thenReturn(ofResult);
        int id = 1;

        // Act
        Integer actualDeactivateUserResult = this.userService.deactivateUser(id);

        // Assert
        assertEquals(1, actualDeactivateUserResult.intValue());
        verify(this.userRepository).findById((Integer) any());
        verify(this.userRepository).save((User) any());
        assertTrue(this.userService.getRoles().isEmpty());
    }

    @Test
    public void testDeactivateUser2() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(false);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);
        Optional<User> ofResult = Optional.<User>of(user);

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");

        Credential credential1 = new Credential();
        credential1.setPassword("iloveyou");
        credential1.setUsername("janedoe");

        Address address1 = new Address();
        address1.setZip(1);
        address1.setCity("Oxford");
        address1.setStreetAddress("42 Main St");
        address1.setState("MD");

        User user1 = new User();
        user1.setLastName("Doe");
        user1.setEmail("jane.doe@example.org");
        user1.setRole(userRole1);
        user1.setDob(LocalDate.ofEpochDay(1L));
        user1.setId(1);
        user1.setActiveStatus(true);
        user1.setPhone("4105551212");
        user1.setCredential(credential1);
        user1.setFirstName("Jane");
        user1.setDateJoined(LocalDate.ofEpochDay(1L));
        user1.setAddress(address1);
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findById((Integer) any())).thenReturn(ofResult);
        int id = 1;

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> this.userService.deactivateUser(id));
        verify(this.userRepository).findById((Integer) any());
    }
}

