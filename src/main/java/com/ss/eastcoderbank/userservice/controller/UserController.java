package com.ss.eastcoderbank.userservice.controller;

import com.ss.eastcoderbank.userservice.dto.UserDTO;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path ="admin/register")
    public Integer manualRegister(@Valid @RequestBody UserDTO udto) {

        return userService.manuallyCreateUser(udto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path="admin/users")
    public List<User> getAllUsers() {

    }
}
