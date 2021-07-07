package com.ss.eastcoderbank.userservice.repository;

import com.ss.eastcoderbank.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository <User, Integer> {


    @Query("FROM User WHERE email = ?1")
    Optional<User> findByEmail(String email);

    @Query("FROM User WHERE username = ?1")
    Optional<User> findByUsername(String username);

    @Query("FROM User WHERE phone = ?1")
    Optional<User> findByPhone(String phone);
}
