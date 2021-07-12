package com.ss.eastcoderbank.userservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@RequiredArgsConstructor
@Setter
@ToString
public class Address {
    @Column(length = 50)
    private String streetAddress;
    @Column(length = 50)
    private String city;
    private Integer zip;
    @Column(length = 2)
    private String state;

}