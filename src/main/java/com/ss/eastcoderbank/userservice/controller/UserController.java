package com.ss.eastcoderbank.userservice.controller;

import com.ss.eastcoderbank.userservice.service.UserService;
import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleConstraintViolation(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public void registration(@RequestBody RegistrationDto user) throws Exception{
        userService.userRegistration(user);
    }

}
