package com.ss.eastcoderbank.userservice.dto;

import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class UserDto {
    private Integer id;

    private UserRole role;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private String email;


    private String phone;

    private Address address;

    private LocalDate dateJoined;

    private boolean activeStatus;

    private String username;
}
