package com.ss.eastcoderbank.userservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
@ToString
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id
    private Integer userID;

    @JoinColumn
    @ManyToOne
    private UserRole userRole;

    @Column(nullable = false, length = 20)
    private String userFirstName;

    @Column(nullable = false, length = 40)
    private String userLastName;

    @Column(nullable = false)
    private Date userDOB;

    @Column(nullable = false, unique = true, length = 50)
    private String userEmail;

    @Column(nullable = false, unique = true, length = 20)
    private String userPhone;

    @Embedded
    private Address userAddress;

    @Column(nullable = false)
    private Date userDateJoined;

    @Column(nullable = false)
    private boolean userActiveStatus;

    @Embedded
    private Credential userCredential;
}
