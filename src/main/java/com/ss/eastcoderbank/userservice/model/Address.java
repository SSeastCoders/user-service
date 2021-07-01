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

    @Column(nullable = false, length = 50)
    private String streetAddress;
    @Column(nullable = false, length = 50)
    private String city;
    @Column(nullable = false)
    private Integer zip;
    @Column(nullable = false, length = 2)
    private String state;
}