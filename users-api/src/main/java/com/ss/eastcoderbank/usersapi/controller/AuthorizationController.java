package com.ss.eastcoderbank.usersapi.controller;

import com.ss.eastcoderbank.usersapi.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthorizationController {

    AuthService authService;

    public AuthorizationController(AuthService authService) {
        this.authService = authService;
    }

}
