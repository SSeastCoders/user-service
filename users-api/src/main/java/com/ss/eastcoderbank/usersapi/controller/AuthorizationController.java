package com.ss.eastcoderbank.usersapi.controller;

import com.ss.eastcoderbank.usersapi.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthorizationController {

    AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

}
