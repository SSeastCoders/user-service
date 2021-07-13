package com.ss.eastcoderbank.userservice.dto;


import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Getter
@Setter
public class UserDto {
    private Integer id;

    private UserRole role;

    private String firstname;

    private String lastname;

    private LocalDate dob;

    @Email
    private String email;

    private String phone;

    private Address address;

    private LocalDate dateJoined;

    private boolean activeStatus;

    private Credential credential;
}
