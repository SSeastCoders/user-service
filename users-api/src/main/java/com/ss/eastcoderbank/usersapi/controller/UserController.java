package com.ss.eastcoderbank.usersapi.controller;


import com.ss.eastcoderbank.core.model.user.UserRole;
import com.ss.eastcoderbank.core.transferdto.UserDto;
import com.ss.eastcoderbank.usersapi.dto.CreateUserDto;
import com.ss.eastcoderbank.usersapi.dto.UpdateProfileDto;
import com.ss.eastcoderbank.usersapi.service.UserService;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
@Slf4j
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;


    @PreAuthorize("permitAll()")
    @GetMapping("/users/health")
    @ResponseStatus(HttpStatus.OK)
    public void healthCheck() { }

    /**
     * @param role
     * @param pageNumber
     * @param pageSize
     * @param asc
     * @param sort
     * @param page
     * @param keyword
     * @return ResponseEntity with page of users
     */
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/users")
    public ResponseEntity<Page<UserDto>> getUsers(@RequestParam(required = false) String role, @RequestParam(name="page") Integer pageNumber, @RequestParam(name="size") Integer pageSize, @RequestParam(value="asc", required = false) boolean asc, @RequestParam(value = "sort", required = false) String sort, Pageable page, @Param("keyword") String keyword) {
        LOGGER.trace("UserController.getUsers endpoint reached...");
        LOGGER.debug("role: "+ role +" page number: "+ pageNumber +" page size: "+ pageSize + " asc?: "+ asc + " sorted by: "+ sort + " keyword: " + keyword);

        if (role != null) {
            return new ResponseEntity<>(userService.getUsersByRole(role, page), HttpStatus.OK);
        }
        if (keyword != null) {
            return new ResponseEntity<>(userService.searchUsers(keyword, pageNumber, pageSize), HttpStatus.OK);
        }
        LOGGER.info("Getting page of users from UserService...");
        return new ResponseEntity<>(userService.getUsers(pageNumber, pageSize, asc, sort), HttpStatus.OK);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @return a page of active users
     */
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/users/active")
    public Page<UserDto> getActiveUsers(@RequestParam(name="page") Integer pageNumber, @RequestParam(name="size") Integer pageSize) {
        LOGGER.trace("UserController.getActiveUsers endpoint reached...");
        LOGGER.debug("page: "+ pageNumber + " size: "+ pageSize);
        LOGGER.info("Getting page of active users from UserService...");
        return userService.getActiveUsers(pageNumber, pageSize);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @return a page of inactive users
     */
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/users/inactive")
    public Page<UserDto> getInactiveUsers(@RequestParam(name="page") Integer pageNumber, @RequestParam(name="size") Integer pageSize) {
        LOGGER.trace("UserController.getInactiveUsers");
        LOGGER.debug("page: "+ pageNumber + " size: "+ pageSize);
        LOGGER.info("Getting inactive users...");
        return userService.getInactiveUsers(pageNumber, pageSize);
    }

/*    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/users/search")
    public ResponseEntity<List<UserDto>> searchUsers(@Param("keyword") String keyword, @RequestParam(name="page") Integer pageNumber, @RequestParam(name="size") Integer pageSize, Pageable page){
        return new ResponseEntity<>(userService.searchUsers(keyword, pageNumber, pageSize), HttpStatus.OK);
    }*/

    /**
     * @param id
     * @return a user based on ID
     */
    @PreAuthorize("principal == #id or hasAuthority('Admin')")
    @GetMapping(value = "/users/{id}")
    public UserDto getUserById(@PathVariable Integer id) {
        LOGGER.trace("UserController.getUserById");
        LOGGER.debug("id: "+ id);
        LOGGER.info("Getting user with id "+ id+ "...");
        return userService.getUserById(id);
    }


    @PreAuthorize("(principal == #id and hasAuthority('Customer')) or hasAuthority('Admin')")
    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUserProfile(@Valid @RequestBody UpdateProfileDto updateProfileDto, @PathVariable Integer id) {
        LOGGER.trace("UserController.updateUserProfile");
        LOGGER.debug("id: "+ id);
        LOGGER.info("Updating user profile with id "+ id + "...");
        userService.updateUser(updateProfileDto, id);
        return new ResponseEntity<>("User updated", HttpStatus.PARTIAL_CONTENT);
    }

    /**
     * @param user
     * @param response
     */
    @PreAuthorize("permitAll()")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public void registration(@Valid @RequestBody CreateUserDto user, HttpServletResponse response) {
        LOGGER.trace("UserController.registration");
        LOGGER.info("Registering user...");
        Integer id = userService.createUser(user);
        response.addHeader("id", String.valueOf(id));
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/users/roles")
    public List<UserRole> getRoles() {
        LOGGER.trace("UserController.getRoles");
        LOGGER.info("Getting roles...");
        return userService.getRoles();
    }

    /**
     * @param id
     * @return ResponseEntity
     */
    @PreAuthorize("principal == #id or hasAuthority('Admin')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable Integer id) {
        LOGGER.trace("UserController.deactivateUser");
        LOGGER.debug("Id: "+ id);
        userService.deactivateUser(id);
        LOGGER.info("User deactivated.");
        return new ResponseEntity<>("User deleted", HttpStatus.NO_CONTENT);
    }

}
