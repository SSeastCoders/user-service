package com.ss.eastcoderbank.userservice.service;

import com.ss.eastcoderbank.userservice.exceptions.DuplicateEmailException;
import com.ss.eastcoderbank.userservice.exceptions.DuplicatePhoneException;
import com.ss.eastcoderbank.userservice.exceptions.DuplicateUsernameException;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import com.ss.eastcoderbank.userservice.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserRoleRepository userRoleRepo;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public User manuallyCreateUser(User user) throws DuplicateEmailException, DuplicateUsernameException, DuplicatePhoneException {
        UserRole role = userRoleRepo.findUserRoleByTitle("administrator").orElse(new UserRole("administrator"));
        user.setRole(role);
        user.setDataJoined(LocalDate.now());

        checkForDuplicates(user);

        String encodedPassword = bCryptPasswordEncoder.encode(user.getCredential().getPassword());
        user.getCredential().setPassword(encodedPassword);

        return userRepo.saveAndFlush(user);

    }

    private void checkForDuplicates(User userFields) throws DuplicatePhoneException, DuplicateEmailException, DuplicateUsernameException {
        boolean emailTaken = userRepo.findByEmail(userFields.getEmail()).isPresent();
        if (emailTaken) {
            throw new DuplicateEmailException("Email already taken!");
        }
        boolean usernameTaken = userRepo.findByUsername(userFields.getCredential().getUsername()).isPresent();
        if (usernameTaken) {
            throw new DuplicateUsernameException("username already taken!");
        }
        boolean phoneTaken = userRepo.findByPhone(userFields.getPhone()).isPresent();
        if (phoneTaken) {
            throw new DuplicatePhoneException("Phone number already associated with a registered user!");
        }
    }

}


