package com.ss.eastcoderbank.userservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
@ToString
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    private UserRole role;

    @Column(length = 20, name = "first_name")
    private String firstName;

    @Column(length = 40, name = "last_name")
    private String lastName;

    private LocalDate dob;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(unique = true, length = 20)
    private String phone;

    @Embedded
    private Address address;

    @Column(name = "date_joined")
    private LocalDate dataJoined;

    @Column(name = "active_status")
    private boolean activeStatus;

    @Embedded
    private Credential credential;
}
