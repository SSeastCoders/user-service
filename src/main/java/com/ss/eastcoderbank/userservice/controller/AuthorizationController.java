package com.ss.eastcoderbank.userservice.controller;

import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthorizationController {

    AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService){
        this.authorizationService = authorizationService;
    }
/*
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public Integer loginUser() {
        return 1;
    }
*/
}
