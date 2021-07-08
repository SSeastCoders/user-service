package com.ss.eastcoderbank.userservice.manualRegistration;

import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final UserRole role;

    private final String firstName;

    private final String lastName;

    private final LocalDate dob;

    private final String email;

    private final String phone;

    private final Address address;

    private final LocalDate dateJoined;

    private final Credential credential;


}
