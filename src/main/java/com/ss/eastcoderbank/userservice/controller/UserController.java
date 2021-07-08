package com.ss.eastcoderbank.userservice.controller;

import com.ss.eastcoderbank.userservice.dto.LoginDto;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.userservice.service.UserService;
import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public void loginUser(@Valid @RequestBody LoginDto login){

    }


    @GetMapping("/user")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@Valid @RequestBody RegistrationDto user) throws DuplicateConstraintsException {
        userService.userRegistration(user);
    }

    @GetMapping("/admin/roles")
    public List<UserRole> getRoles() {
        return userService.getRoles();
    }

}
