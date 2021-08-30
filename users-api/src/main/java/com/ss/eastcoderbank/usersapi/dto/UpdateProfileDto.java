package com.ss.eastcoderbank.usersapi.dto;

import com.ss.eastcoderbank.usersapi.validation.annotation.ValidPhoneNumber;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateProfileDto {
    @Pattern(regexp = "[a-z0-9A-Z]+", message = "username can have only alphanumeric characters.")
    @Size(min = 1, max = 20, message = "username must not be blank and contain no more than 20 characters.")
    private String username;

    @Size(min = 6, max = 20, message = "password must contain at least 6 characters and be no more than 20.")
    private String password;

    @Email(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$", message="Please provide a valid email address.")
    private String email;

    private AddressDto address;

    @ValidPhoneNumber
    private String phone;

    private String role;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private LocalDate dateJoined;

    private Boolean activeStatus;
}
