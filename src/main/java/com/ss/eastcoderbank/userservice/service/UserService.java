package com.ss.eastcoderbank.userservice.service;

import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.dto.UserDto;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import com.ss.eastcoderbank.userservice.repository.UserRoleRepository;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateEmailException;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateUsernameException;
import com.ss.eastcoderbank.userservice.service.constraints.Constraints;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void userRegistration(RegistrationDto registrationDto) throws DuplicateConstraintsException {
        User user = registrationToUser(registrationDto);
        UserRole role = userRoleRepository.findUserRoleByTitle("customer").orElse(new UserRole("customer"));
        user.setRole(role);
        user.setDataJoined(LocalDate.now());
        // EDIT TO SAVE PASSWORD AS ENCRYPTED HASH - hbh
        Credential credential = user.getCredential();
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        user.setCredential(credential);
        // end of edit to save password as hash
        try {
            userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            Throwable t = e.getCause();
            if (t instanceof ConstraintViolationException) {
                handleUniqueConstraints(((ConstraintViolationException) t).getConstraintName());
            }
            throw e; // something went wrong.
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<UserRole> getRoles() {
        return userRoleRepository.findAll();
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    //Needs to be tested
    private User registrationToUser(RegistrationDto registrationDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(registrationDto, User.class);
    }

    private void handleUniqueConstraints(String constraint) throws DuplicateConstraintsException, DuplicateEmailException, DuplicateUsernameException {
        String constraintLower = constraint.toLowerCase();
        if (constraintLower.contains(Constraints.EMAILANDUSERNAME)) throw new DuplicateConstraintsException("duplicate username and email");
        else if (constraintLower.contains(Constraints.EMAIL)) throw new DuplicateEmailException("duplicate email");
        else if (constraintLower.contains(Constraints.USERNAME)) throw new DuplicateUsernameException("duplicate username");
    }
}
