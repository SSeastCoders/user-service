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
    @Column(nullable = false, length = 20)
    private String username;
// EDIT TO ALLOW FOR HASH LENGTH
    @Column(nullable = false, length = 100)
    private String password;

}
