package com.ss.eastcoderbank.userservice.dto;

import com.ss.eastcoderbank.userservice.model.Credential;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDto {
    private String username;
    private String password;
    private String email;
}
