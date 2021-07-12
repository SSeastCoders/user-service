package com.ss.eastcoderbank.userservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class Credential {

    @Column(nullable = false, unique = true, length = 50)
    public String username;
    @Column(nullable = false, length = 250)
    public String password;


}
