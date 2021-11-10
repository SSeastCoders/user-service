package com.ss.eastcoderbank.usersapi.controller;

import com.ss.eastcoderbank.core.model.user.Address;
import com.ss.eastcoderbank.core.model.user.Credential;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.model.user.UserRole;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.core.repository.UserRoleRepository;
import com.ss.eastcoderbank.core.transferdto.UserDto;
import com.ss.eastcoderbank.core.transfermapper.UserMapper;
import com.ss.eastcoderbank.usersapi.dto.AddressDto;
import com.ss.eastcoderbank.usersapi.dto.CreateUserDto;
import com.ss.eastcoderbank.usersapi.dto.UpdateProfileDto;
import com.ss.eastcoderbank.usersapi.mapper.CreateUserMapper;
import com.ss.eastcoderbank.usersapi.mapper.UpdateProfileMapper;
import com.ss.eastcoderbank.usersapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

import javax.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    UserController userController;

    @MockBean
    private UserService userService;


    @MockBean
    private Validator validator;

    @Test
    void testRegistration() {
        //List userArr = new ArrayList<User>();
        CreateUserDto newUser = new CreateUserDto();
        Integer id = userService.createUser(newUser);
        when(this.userService.createUser((CreateUserDto) any())).thenReturn(1);
        assertEquals(1, 1);
        //assertFalse(this.userService.getUsers(1,10,false, "sort").isEmpty());
    }


    @Test
    void testGetUserById() throws Exception {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserDto userDto = new UserDto();
        userDto.setLastName("Doe");
        userDto.setEmail("jane.doe@example.org");
        userDto.setRole(userRole);
        userDto.setDob(LocalDate.ofEpochDay(1L));
        userDto.setUsername("janedoe");
        userDto.setId(1);
        userDto.setActiveStatus(true);
        userDto.setPhone("4105551212");
        userDto.setFirstName("Jane");
        userDto.setDateJoined(LocalDate.ofEpochDay(1L));
        userDto.setAddress(address);
        when(this.userService.getUserById((Integer) any())).thenReturn(userDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"role\":{\"id\":1,\"title\":\"Dr\"},\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"dob\":[1970,1,2],\"email\":"
                                        + "\"jane.doe@example.org\",\"phone\":\"4105551212\",\"address\":{\"streetAddress\":\"42 Main St\",\"city\":\"Oxford\","
                                        + "\"zip\":1,\"state\":\"MD\"},\"dateJoined\":[1970,1,2],\"activeStatus\":true,\"username\":\"janedoe\"}"));
    }



    @Test
    void testUpdateUserProfile() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.getById((Integer) any())).thenReturn(user);

        UserRole userRole2 = new UserRole();
        userRole2.setUsers(new HashSet<User>());
        userRole2.setId(1);
        userRole2.setTitle("Dr");
        UserRoleRepository userRoleRepository = mock(UserRoleRepository.class);
        when(userRoleRepository.findUserRoleByTitle(anyString())).thenReturn(Optional.<UserRole>of(userRole2));
        UpdateProfileMapper updateProfileMapper = mock(UpdateProfileMapper.class);
        doNothing().when(updateProfileMapper)
                .updateEntity((UpdateProfileDto) any(), (User) any(),
                        (org.springframework.security.crypto.password.PasswordEncoder) any());
        UserService userService = new UserService(userRepository, userRoleRepository, new Argon2PasswordEncoder(),
                mock(UserMapper.class), updateProfileMapper, mock(CreateUserMapper.class));

        UserController userController = new UserController(userService, new CustomValidatorBean());

        AddressDto addressDto = new AddressDto();
        addressDto.setZip(1);
        addressDto.setCity("Oxford");
        addressDto.setStreetAddress("42 Main St");
        addressDto.setState("MD");

        UpdateProfileDto updateProfileDto = new UpdateProfileDto();
        updateProfileDto.setLastName("Doe");
        updateProfileDto.setPassword("iloveyou");
        updateProfileDto.setEmail("jane.doe@example.org");
        updateProfileDto.setRole("Role");
        updateProfileDto.setDob(LocalDate.ofEpochDay(1L));
        updateProfileDto.setUsername("janedoe");
        updateProfileDto.setActiveStatus(true);
        updateProfileDto.setPhone("4105551212");
        updateProfileDto.setFirstName("Jane");
        updateProfileDto.setAddress(addressDto);
        updateProfileDto.setDateJoined(LocalDate.ofEpochDay(1L));
        int id = 1;

        // Act
        ResponseEntity<String> actualUpdateUserProfileResult = userController.updateUserProfile(updateProfileDto, id);

        // Assert
        assertEquals("User updated", actualUpdateUserProfileResult.getBody());
        assertEquals("<206 PARTIAL_CONTENT Partial Content,User updated,[]>", actualUpdateUserProfileResult.toString());
        assertEquals(HttpStatus.PARTIAL_CONTENT, actualUpdateUserProfileResult.getStatusCode());
        assertTrue(actualUpdateUserProfileResult.getHeaders().isEmpty());
        verify(userRepository).getById((Integer) any());
        verify(userRepository).save((User) any());
        verify(userRoleRepository).findUserRoleByTitle(anyString());
        verify(updateProfileMapper).updateEntity((UpdateProfileDto) any(), (User) any(),
                (PasswordEncoder) any());
        assertTrue(userController.getRoles().isEmpty());
    }

    @Test
    void testGetRoles() throws Exception {
        // Arrange
        when(this.userService.getRoles()).thenReturn(new ArrayList<UserRole>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/roles");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetRoles2() throws Exception {
        // Arrange
        when(this.userService.getRoles()).thenReturn(new ArrayList<UserRole>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/users/roles");
        getResult.contentType("Not all who wander are lost");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(getResult);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testDeactivateUser() throws Exception {
        // Arrange
        when(this.userService.deactivateUser((Integer) any())).thenReturn(1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{id}", 1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User deleted"));
    }

    @Test
    void testGetUsers() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/users");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("size", String.valueOf(1));
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }



}

