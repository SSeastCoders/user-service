package com.ss.eastcoderbank.userservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;



import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@EqualsAndHashCode
@Setter
@RequiredArgsConstructor
@ToString
public class UserRole {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;


    @Column(nullable = false, unique = true, length = 20) // title must be unique
    private String title;

    public UserRole(String title) {
        this.title = title;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "role", orphanRemoval = true)
    private Set<User> users = new HashSet<>();



}
