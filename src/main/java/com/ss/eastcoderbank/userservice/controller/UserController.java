package com.ss.eastcoderbank.userservice.controller;


import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.dto.UpdateProfileDto;
import com.ss.eastcoderbank.userservice.dto.UserDto;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.security.JwtAuthenticationFilter;
import com.ss.eastcoderbank.userservice.security.JwtParser;
import com.ss.eastcoderbank.userservice.security.JwtUtil;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
    @PreAuthorize("hasAnyAuthority('Administrator', 'Customer')")
    @GetMapping(value = "/user")
    public User getUserByUsername(HttpServletRequest request) {
        String token =  request.getHeader("Authorization").replace(JwtUtil.JWT_UTIL.getTokenPrefix(), "");
        Integer id =  JwtParser.parseId(token);
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAnyAuthority('Adminstrator', 'Customer')")
    @PutMapping("/user")
    public Integer updateUserProfile(@Valid @RequestBody UpdateProfileDto updateProfileDto, HttpServletRequest request) {
        String token =  request.getHeader("Authorization").replace(JwtUtil.JWT_UTIL.getTokenPrefix(), "");
        Integer id =  JwtParser.parseId(token);
        return userService.updateUser(updateProfileDto, id);

    }

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<Integer> registration(@Valid @RequestBody RegistrationDto user) throws DuplicateConstraintsException {
        return new ResponseEntity<Integer>(userService.userRegistration(user), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('Administrator')")
    @GetMapping("/admin/roles")
    public List<UserRole> getRoles() {
        return userService.getRoles();
    }

    @PreAuthorize("permitAll()")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path ="admin/register")
    public Integer manualRegister(UserDto udto) {

        return userService.manuallyCreateUser(udto);
    }

    @PreAuthorize("hasAuthority('Administrator')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path="admin/users")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @PreAuthorize("hasAuthority('Administrator')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path=("admin/users/{userId}"))
    public Integer updateUserDetails(UserDto userDTO) {
        return userService.updateUserDetails(userDTO);
    }

    @PreAuthorize("hasAuthority('Administrator')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path="admin/users/{userId}")
    public Integer deactivateUser(UserDto userDTO) {

        return userService.deactivateUser(userDTO);
    }

    @PreAuthorize("hasAuthority('Administrator')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path="admin/administrators")
    public List<User> getAllAdmins() {
        return userService.getAllAdmins();

    }

}
