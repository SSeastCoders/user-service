package com.ss.eastcoderbank.userservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.eastcoderbank.userservice.controller.ExceptionMessage.ErrorMessage;
import com.ss.eastcoderbank.userservice.dto.CreateUserDto;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateEmailException;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateUsernameException;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.ExceptionMessages;
import com.ss.eastcoderbank.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasKey;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;


    @Autowired
    private MockMvc mockMvc;



    /**
     * Test to for badquest when validation error is thrown for dto
     * @throws Exception
     */
    @Test void testRegistrationDtoValidation() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setPassword("password");
        createUserDto.setEmail("lovely");
        createUserDto.setUsername("tommy");
        ObjectMapper map = new ObjectMapper();
        String content = map.writeValueAsString(createUserDto);

        when(this.userService.createUser(createUserDto)).thenReturn(1);
        //Bad email must have @ symbol bad request
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(hasKey("email")));

        // null username bad request
        createUserDto.setUsername(null);
        createUserDto.setEmail("hello@gmail.com");
        String contentNoUsername = map.writeValueAsString(createUserDto);
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentNoUsername))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(hasKey("username")));

        // username pattern contains a non Alphanumeric symbol
        CreateUserDto badUserNameSymbol = new CreateUserDto("gegr&&&", "password", "ss@gmail.com");
        String contentBadUsernameSymbol = map.writeValueAsString(badUserNameSymbol);
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentBadUsernameSymbol))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(hasKey("username")));

        // short password test bad request
        CreateUserDto passwordShort = new CreateUserDto("johnny", "hge", "ss@gmail.com");
        String contentShortPassword = map.writeValueAsString(passwordShort);
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentShortPassword))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(hasKey("password")));
    }

}

