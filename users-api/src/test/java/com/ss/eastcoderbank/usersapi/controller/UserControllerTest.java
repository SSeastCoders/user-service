package com.ss.eastcoderbank.usersapi.controller;

import com.ss.eastcoderbank.core.model.user.Address;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.model.user.UserRole;
import com.ss.eastcoderbank.core.transferdto.UserDto;
import com.ss.eastcoderbank.usersapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @MockBean
    private Validator validator;

    @Test
    public void testGetUserById() throws Exception {
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
    public void testGetRoles() throws Exception {
        // Arrange
        when(this.userService.getRoles()).thenReturn(new ArrayList<UserRole>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/roles");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetRoles2() throws Exception {
        // Arrange
        when(this.userService.getRoles()).thenReturn(new ArrayList<UserRole>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/roles");
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
    public void testDeactivateUser() throws Exception {
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
    public void testGetUsers() throws Exception {
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

