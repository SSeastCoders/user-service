package com.ss.eastcoderbank.userservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString
public class Credential {

    @Column(nullable = false, unique = true, length = 50)
    public String username;
    @Column(nullable = false, length = 250)
    public String password;



}
