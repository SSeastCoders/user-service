package com.ss.eastcoderbank.userservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ss.eastcoderbank.userservice.service.constraints.DbConstraints;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
@ToString
@Table(uniqueConstraints = {
        @UniqueConstraint(name = DbConstraints.EMAILANDUSERNAME, columnNames = {"email", "username"}),
        @UniqueConstraint(name = DbConstraints.EMAIL, columnNames = {"email"}),
        @UniqueConstraint(name = DbConstraints.USERNAME, columnNames = {"username"}),
        @UniqueConstraint(name = DbConstraints.PHONE, columnNames = {"phone"}),
})
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.PERSIST)
    private UserRole role;

    @Column(length = 20, name = "first_name")
    private String firstName;

    @Column(length = 40, name = "last_name")
    private String lastName;

    private LocalDate dob;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(length = 20)
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