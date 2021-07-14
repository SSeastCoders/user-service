package com.ss.eastcoderbank.userservice.repository;

import com.ss.eastcoderbank.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

import java.util.Optional;



public interface UserRepository extends JpaRepository <User, Integer> {


    User findUsersByEmailOrCredentialUsername(String email, String Username);

    @Query("FROM User WHERE email = ?1")
    Optional<User> findByEmail(String email);

    @Query("FROM User WHERE username = ?1")
    Optional<User> findByUsername(String username);

    @Query("FROM User WHERE phone = ?1")
    Optional<User> findByPhone(String phone);

    List<User> findUserByRoleId(Integer role_id);


}
