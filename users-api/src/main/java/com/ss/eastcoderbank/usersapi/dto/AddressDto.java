package com.ss.eastcoderbank.usersapi.dto;

import com.ss.eastcoderbank.core.model.user.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
public class AddressDto {
    @Size(max = 50, message = "address too long.")
    private String streetAddress;

    @Size(max = 50, message = "city too long.")
    private String city;
    private Integer zip;

    @Size(min = 2, max = 2, message = "state must have 2 characters.")
    private String state;

    public AddressDto(Address address) {
        this.streetAddress = address.getStreetAddress();
        this.city = address.getCity();
        this.state = address.getState();
        this.zip = address.getZip();
    }
}
