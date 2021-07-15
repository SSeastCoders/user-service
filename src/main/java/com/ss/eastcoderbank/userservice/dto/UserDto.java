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

    @NotBlank(message = "must have a role")
    private UserRole role;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    @Email(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$", message="Please provide a valid email address")
    @NotNull(message = "email must be entered")
    private String email;


    @Size(min = 12, max = 12, message = "Please provide a 10 digit phone number")
    private String phone;

    private Address address;

    private LocalDate dateJoined;

    private boolean activeStatus;

    private Credential credential;
}
