package com.ss.eastcoderbank.userservice.controller;


import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.dto.UserDto;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public List<User> getUsers() {
        return userService.getUsers();
    }


    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<Integer> registration(@Valid @RequestBody RegistrationDto user) throws DuplicateConstraintsException {
        return new ResponseEntity<Integer>(userService.userRegistration(user), HttpStatus.CREATED);
    }

    @GetMapping("/admin/roles")
    public List<UserRole> getRoles() {
        return userService.getRoles();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path ="admin/register")
    public Integer manualRegister(UserDto udto) {

        return userService.manuallyCreateUser(udto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path="admin/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path=("admin/users/{userId}"))
    public Integer updateUserDetails(UserDto userDTO) {
        return userService.updateUserDetails(userDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path="admin/users/{userId}")
    public Integer deactivateUser(UserDto userDTO) {

        return userService.deactivateUser(userDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path="admin/administrators")
    public List<User> getAllAdmins() {
        return userService.getAllAdmins();

    }

}
