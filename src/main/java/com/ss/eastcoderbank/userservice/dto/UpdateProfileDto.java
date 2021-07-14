package com.ss.eastcoderbank.userservice.dto;

import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateProfileDto {
    private String firstName;

    private String lastName;

    private LocalDate dob;

    @Email(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$", message="Please provide a valid email address")
    private String email;

    private String phone;

    private AddressDto address;

    private LocalDate dateJoined;

    @Size(min = 6, max = 20, message = "password must contain at least 6 characters and be no more than 20")
    private String password;
}
