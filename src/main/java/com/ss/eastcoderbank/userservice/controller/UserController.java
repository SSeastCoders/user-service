package com.ss.eastcoderbank.userservice.controller;


import com.ss.eastcoderbank.userservice.dto.CreateUserDto;
import com.ss.eastcoderbank.userservice.dto.UpdateProfileDto;
import com.ss.eastcoderbank.userservice.dto.UserDto;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam(required = false) String role) {
        // TODO implement pagination
        if (role != null) return userService.getUsersByRole(role);
        return userService.getUsers();
    }

    //HYPOTHETICAL BASED ON USER ID
    @PreAuthorize("principal == #id or hasAuthority('Admin')")
    @GetMapping(value = "/users/{id}")
    public UserDto getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("(principal == #id and hasAuthority('Customer')) or hasAuthority('Admin')")
    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUserProfile(@Valid @RequestBody UpdateProfileDto updateProfileDto, @PathVariable Integer id) {
        userService.updateUser(updateProfileDto, id);
        return new ResponseEntity<>("User updated", HttpStatus.PARTIAL_CONTENT);
    }

    @PreAuthorize("permitAll()")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public void registration(@Valid @RequestBody CreateUserDto user, HttpServletResponse response) {
        Integer id = userService.createUser(user);
        response.addHeader("id", String.valueOf(id));
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/roles")
    public List<UserRole> getRoles() {
        // TODO implement pagination
        return userService.getRoles();
    }

    @PreAuthorize("principal == #id or hasAuthority('Admin')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable Integer id) {
        userService.deactivateUser(id);
        return new ResponseEntity<>("User deleted", HttpStatus.NO_CONTENT);
    }

}
