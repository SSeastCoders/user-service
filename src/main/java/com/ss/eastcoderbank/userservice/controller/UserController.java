package com.ss.eastcoderbank.userservice.controller;

import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.userservice.service.UserService;
import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('Administrator')")
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    //HYPOTHETICAL BASED ON USER ID
    @PreAuthorize("#id == principal or hasAuthority('Administrator')")
    @GetMapping(value = "/user/{id}")
    public User getUserByUsername(@PathVariable String id) {
        return userService.getUserByUsername(id);
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@Valid @RequestBody RegistrationDto user) throws DuplicateConstraintsException {
        userService.userRegistration(user);
    }

    @PreAuthorize("hasAuthority('Administrator')")
    @GetMapping("/admin/roles")
    public List<UserRole> getRoles() {
        return userService.getRoles();
    }

}
