package com.ss.eastcoderbank.userservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class UserRole {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(nullable = false, unique = true, length = 20) // title must be unique
    private String title;
    @JsonManagedReference
    @OneToMany(mappedBy = "role", orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    public UserRole(String title) {
        this.title = title;
    }

}
