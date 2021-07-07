package com.ss.eastcoderbank.userservice.dto;

import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {
    private Integer id;

    private UserRole role;

    private String firstname;

    private String lastname;

    private LocalDate dob;

    private String email;

    private String phone;

    private Address address;

    private LocalDate dateJoined;

    private boolean activeStatus;

    private Credential credential;


}
