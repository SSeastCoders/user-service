package com.ss.eastcoderbank.userservice.dto;

import com.ss.eastcoderbank.userservice.model.Credential;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class RegistrationDto {
    @Pattern(regexp = "[a-z0-9A-Z]+", message = "username can have only alphanumeric characters")
    @Size(min = 1, max = 20, message = "username must not be blank and contain no more than 20 characters")
    @NotNull(message = "username must be entered")
    private String username;

    @Size(min = 6, max = 20, message = "password must contain at least 6 characters and be no more than 20")
    @NotBlank(message = "password must be entered")
    private String password;

    @Email(message = "not a valid email")
    @NotNull(message = "email must be entered")
    private String email;
}
