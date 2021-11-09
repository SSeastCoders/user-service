package com.ss.eastcoderbank.usersapi;

import com.ss.eastcoderbank.usersapi.controller.AuthorizationController;
import com.ss.eastcoderbank.usersapi.controller.ExceptionController;
import com.ss.eastcoderbank.usersapi.controller.UserController;
import com.ss.eastcoderbank.usersapi.service.AuthService;
import com.ss.eastcoderbank.usersapi.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceApplicationTests {

    @Autowired
    private UserController userController;

    @Autowired
    private AuthorizationController authorizationController;

    @Autowired
    private ExceptionController exceptionController;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Test
    void contextLoads() {
        assertThat(userController).isNotNull();
        assertThat(authorizationController).isNotNull();
        assertThat(exceptionController).isNotNull();
        assertThat(authService).isNotNull();
        assertThat(userService).isNotNull();
    }

}
