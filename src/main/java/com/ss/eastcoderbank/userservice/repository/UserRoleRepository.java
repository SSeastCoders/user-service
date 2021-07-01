package com.ss.eastcoderbank.userservice.repository;

import com.ss.eastcoderbank.userservice.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository <UserRole, Integer> {
}
