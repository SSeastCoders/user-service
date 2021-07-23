package com.ss.eastcoderbank.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class AddressDto {
    @Size(max = 50, message = "address too long.")
    private String streetAddress;

    @Size(max = 50, message = "city too long.")
    private String city;
    private Integer zip;

    @Size(min = 2, max = 2, message = "state must have 2 characters.")
    private String state;
}
