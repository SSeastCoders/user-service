package com.ss.eastcoderbank.userservice.controller;

import com.ss.eastcoderbank.userservice.dto.UserDto;
import com.ss.eastcoderbank.userservice.dto.validationgroups.RegistrationGroup;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.userservice.service.UserService;
import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.model.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
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

}
