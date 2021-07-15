package com.ss.eastcoderbank.userservice.controller;


import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.dto.UpdateProfileDto;
import com.ss.eastcoderbank.userservice.dto.UserDto;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.security.JwtAuthenticationFilter;
import com.ss.eastcoderbank.userservice.security.JwtParser;
import com.ss.eastcoderbank.userservice.security.JwtUtil;
import com.ss.eastcoderbank.userservice.security.UserPrincipal;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    //HYPOTHETICAL BASED ON USER ID
    @PreAuthorize("hasAnyAuthority('Admin', 'Customer')")
    @GetMapping(value = "/user")
    public User getUserByUsername(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace(JwtUtil.JWT_UTIL.getTokenPrefix(), "");
        Integer id = JwtParser.parseId(token);
        return userService.getUserById(id);
    }

    @PreAuthorize("(principal == #id and hasAuthority('Customer')) or hasAuthority('Admin')")
    @PutMapping("/users/{id}")
    public Integer updateUserProfile(@Valid @RequestBody UpdateProfileDto updateProfileDto, @PathVariable Integer id) {
        return userService.updateUser(updateProfileDto, id);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity<Integer> registration(@Valid @RequestBody RegistrationDto user) {
        return new ResponseEntity<Integer>(userService.userRegistration(user), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/roles")
    public List<UserRole> getRoles() {
        return userService.getRoles();
    }

//    @PreAuthorize("permitAll()")
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping(path = "admin/register")
//    public Integer manualRegister(UserDto udto) {
//
//        return userService.manuallyCreateUser(udto);
//    }

    @PreAuthorize("hasAuthority('Admin')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "admin/users")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

//    @PreAuthorize("hasAuthority('Admin')")
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping(path = ("admin/user/{id}"))
//    public Integer updateUserDetails(UserDto userDTO) {
//        return userService.updateUserDetails(userDTO);
//    }

//    @PreAuthorize("hasAuthority('Admin')")
//    @ResponseStatus(HttpStatus.OK)
//    @PatchMapping(path = "admin/users/{userId}")
//    public Integer deactivateUser(UserDto userDTO) {
//
//        return userService.deactivateUser(userDTO);
//    }

    @PreAuthorize("hasAuthority('Admin')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "admin/administrators")
    public List<User> getAllAdmins() {
        return userService.getAllAdmins();

    }

}