package com.ss.eastcoderbank.userservice.controller;

import com.ss.eastcoderbank.userservice.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthorizationController {

    AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService){
        this.authorizationService = authorizationService;
    }

    //@PreAuthorize("permitAll")
    //@PostMapping(value ="/login")
    //@ResponseBody
    //public ResponseEntity<UserAuthorizedDto> loginUser(@RequestBody(required = true) @Valid UserLoginDto login) throws InvalidCredentialsException {
    //    return ResponseEntity.ok(new UserAuthorizedDto(Jwts.builder()));//authorizationService.
    //}
}
