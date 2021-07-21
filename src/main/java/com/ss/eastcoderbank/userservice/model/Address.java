package com.ss.eastcoderbank.userservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
public class Address {
    @Column(length = 50)
    private String streetAddress;
    @Column(length = 50)
    private String city;
    private Integer zip;
    @Column(length = 2)
    private String state;

}