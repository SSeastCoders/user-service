package com.ss.eastcoderbank.usersapi.controller;


import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.model.user.UserRole;
import com.ss.eastcoderbank.core.transferdto.UserDto;
import com.ss.eastcoderbank.usersapi.dto.CreateUserDto;
import com.ss.eastcoderbank.usersapi.dto.UpdateProfileDto;
import com.ss.eastcoderbank.usersapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@CrossOrigin(origins = "*")
@AllArgsConstructor

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/users")

    public Page<UserDto> getUsers(@RequestParam(required = false) String role, @RequestParam(name="page") Integer pageNumber, @RequestParam(name="size") Integer pageSize, @RequestParam(value="asc", required = false) boolean asc, @RequestParam(value = "sort", required = false) String sort, Pageable page) {
        if (role != null) return userService.getUsersByRole(role, page);

        return userService.getUsers(pageNumber, pageSize, asc, sort);

    }

 /*   @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/users/active")
    public Page<UserDto> getActiveUsers(@RequestParam(name="page") Integer pageNumber, @RequestParam(name="size") Integer pageSize) {
        return userService.getActiveUsers(pageNumber, pageSize);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/users/inactive")
    public Page<UserDto> getInactiveUsers(@RequestParam(name="page") Integer pageNumber, @RequestParam(name="size") Integer pageSize) {
        return userService.getInactiveUsers(pageNumber, pageSize);
    }*/





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
    @GetMapping("/users/roles")
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
