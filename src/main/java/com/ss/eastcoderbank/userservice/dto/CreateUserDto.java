package com.ss.eastcoderbank.userservice.dto;

import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.validation.annotation.ValidPhoneNumber;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CreateUserDto {
    @Pattern(regexp = "[a-z0-9A-Z]+", message = "username can have only alphanumeric characters.")
    @Size(min = 1, max = 20, message = "username must not be blank and contain no more than 20 characters.")
    @NotNull(message = "username must be entered.")
    private String username;

    @Size(min = 6, max = 20, message = "password must contain at least 6 characters and be no more than 20.")
    @NotBlank(message = "password must be entered.")
    private String password;

    @Email(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$", message="Please provide a valid email address.")
    @NotNull(message = "email must be entered.")
    private String email;

    private AddressDto address;

    @ValidPhoneNumber
    private String phone;

    private String Role;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private LocalDate dateJoined;

    private boolean activeStatus;

    public CreateUserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public CreateUserDto(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

}
