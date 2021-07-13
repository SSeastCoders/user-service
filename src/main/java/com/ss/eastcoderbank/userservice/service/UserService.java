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
import com.ss.eastcoderbank.userservice.service.CustomExceptions.ExceptionMessages;
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
    ModelMapper modelMapper;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Integer userRegistration(RegistrationDto registrationDto) throws DuplicateConstraintsException {
        String password = passwordEncoder.encode(registrationDto.getPassword());
        registrationDto.setPassword(password);
        User user = registrationToUser(registrationDto);
        UserRole role = userRoleRepository.findUserRoleByTitle("customer").orElse(new UserRole("customer"));
        user.setRole(role);
        user.setDataJoined(LocalDate.now());
        try {
            userRepository.save(user);
            return user.getId();
        } catch (DataIntegrityViolationException e) {
            Throwable t = e.getCause();
            if (t instanceof ConstraintViolationException) {
                handleUniqueConstraints(((ConstraintViolationException) t).getConstraintName());
            }
            throw e; // smothing went wrong.
        }
    }



    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<UserRole> getRoles() {
        return userRoleRepository.findAll();
    }

    protected User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    //Needs to be tested
    protected User registrationToUser(RegistrationDto registrationDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        User user = modelMapper.map(registrationDto, User.class);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return user;
    }

    protected void handleUniqueConstraints(String constraint) throws DuplicateConstraintsException, DuplicateEmailException, DuplicateUsernameException {
        String constraintLower = constraint.toLowerCase();
        if (constraintLower.contains(Constraints.EMAILANDUSERNAME)) throw new DuplicateConstraintsException(ExceptionMessages.USERNAMEANDEMAIL);
        else if (constraintLower.contains(Constraints.EMAIL)) throw new DuplicateEmailException(ExceptionMessages.EMAIL);
        else if (constraintLower.contains(Constraints.USERNAME)) throw new DuplicateUsernameException(ExceptionMessages.USERNAME);
    }
}
