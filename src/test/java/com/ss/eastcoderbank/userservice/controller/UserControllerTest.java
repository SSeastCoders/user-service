package com.ss.eastcoderbank.userservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasKey;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.eastcoderbank.userservice.controller.ExceptionMessage.ErrorMessage;
import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateEmailException;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateUsernameException;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.ExceptionMessages;
import com.ss.eastcoderbank.userservice.service.UserService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest()
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetUsers() throws Exception {
        when(this.userService.getUsers()).thenReturn(new ArrayList<User>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"));
    }

    @Test
    public void testGetUsers2() throws Exception {
        when(this.userService.getUsers()).thenReturn(new ArrayList<User>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"));
    }

    @Test
    public void testGetRoles() throws Exception {
        when(this.userService.getRoles()).thenReturn(new ArrayList<UserRole>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/roles");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"));
    }

    @Test
    public void testRegistration() throws Exception {
        when(this.userService.userRegistration((RegistrationDto) any())).thenReturn(1);

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("iloveyou");
        registrationDto.setEmail("jane.doe@example.org");
        registrationDto.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(registrationDto);
        MockHttpServletRequestBuilder requestBuilder = post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("1"));
    }

    @Test
    public void testRegistrationEmailDuplicate() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("password");
        registrationDto.setEmail("hello@gmail.com");
        registrationDto.setUsername("tommy");
        ObjectMapper map = new ObjectMapper();
        String content = map.writeValueAsString(registrationDto);

        String duplicateEmail = map.writeValueAsString(new ErrorMessage(HttpStatus.CONFLICT.toString(), ExceptionMessages.EMAIL));


        when(this.userService.userRegistration(registrationDto)).thenThrow(new DuplicateEmailException());
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(duplicateEmail));
    }

    @Test
    public void testRegistrationUsernameDuplicate() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("password");
        registrationDto.setEmail("hello@gmail.com");
        registrationDto.setUsername("tommy");
        ObjectMapper map = new ObjectMapper();
        String content = map.writeValueAsString(registrationDto);

        String duplicateUsername = map.writeValueAsString(new ErrorMessage(HttpStatus.CONFLICT.toString(), ExceptionMessages.USERNAME));
        when(this.userService.userRegistration(registrationDto)).thenThrow(new DuplicateUsernameException());
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(duplicateUsername));
    }

    /**
     * Test to for badquest when validation error is thrown for dto
     * @throws Exception
     */
    @Test void testRegistrationDtoValidation() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("password");
        registrationDto.setEmail("lovely");
        registrationDto.setUsername("tommy");
        ObjectMapper map = new ObjectMapper();
        String content = map.writeValueAsString(registrationDto);

        when(this.userService.userRegistration(registrationDto)).thenReturn(1);
        //Bad email must have @ symbol bad request
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(hasKey("email")));

        // null username bad request
        registrationDto.setUsername(null);
        registrationDto.setEmail("hello@gmail.com");
        String contentNoUsername = map.writeValueAsString(registrationDto);
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentNoUsername))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(hasKey("username")));

        // username pattern contains a non Alphanumeric symbol
        RegistrationDto badUserNameSymbol = new RegistrationDto("gegr&&&", "password", "ss@gmail.com");
        String contentBadUsernameSymbol = map.writeValueAsString(badUserNameSymbol);
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentBadUsernameSymbol))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(hasKey("username")));

        // short password test bad request
        RegistrationDto passwordShort = new RegistrationDto("johnny", "hge", "ss@gmail.com");
        String contentShortPassword = map.writeValueAsString(passwordShort);
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentShortPassword))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(hasKey("password")));
    }
}

