package com.ss.eastcoderbank.usersapi.controller;

import com.ss.eastcoderbank.usersapi.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthorizationController {

    AuthService authorizationService;

    public AuthorizationController(AuthService authorizationService) {
        this.authorizationService = authorizationService;
    }
//    @PreAuthorize("permitAll()")
//    @PostMapping("/login")
//    public Integer loginUser() {
//        return 1;
//    }
}
