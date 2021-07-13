package com.ss.eastcoderbank.userservice.controller;

import static org.mockito.Mockito.when;

import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.service.UserService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    public void testGetAllUsers() throws Exception {
        when(this.userService.getAllUsers()).thenReturn(new ArrayList<User>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/users");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllUsers2() throws Exception {
        when(this.userService.getAllUsers()).thenReturn(new ArrayList<User>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/users");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllAdmins() throws Exception {
        when(this.userService.getAllAdmins()).thenReturn(new ArrayList<User>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/administrators");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

