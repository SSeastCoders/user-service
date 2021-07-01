package com.ss.eastcoderbank.userservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class UserRole {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id
    private Integer userRoleID;

    @Column(nullable = false, unique = true, length = 20)
    private String userRoleTitle;

    @ToString.Exclude
    @OneToMany(mappedBy = "userRole", orphanRemoval = true)
    private Set<User> users;

}
