package com.ss.eastcoderbank.userservice.controller;

import com.ss.eastcoderbank.userservice.dto.UserDTO;
import com.ss.eastcoderbank.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
//@RequestMapping(path ="admin/register")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path ="admin/register")
    public Integer manualRegistration(@Valid @RequestBody UserDTO udto) {
        return userService.manuallyCreateUser(udto);
    }


}
