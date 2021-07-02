package com.ss.eastcoderbank.userservice.service;

import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.dto.UserDto;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    public void userRegistration(RegistrationDto registrationDto) throws Exception {
        User user = registrationToUser(registrationDto);
        UserRole role = new UserRole();
        role.setTitle("customer");
        user.setRole(role);
        User userExist = userRepository.findUsersByEmailOrCredentialUsername(user.getEmail(), user.getCredential().getUsername());
        if (userExist == null)
            userRepository.saveAndFlush(user);
        else if (userExist.getEmail().equals(user.getEmail())) {
            throw new Exception("Duplicate email");
        } else {
            throw new Exception("Duplicate username");
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private User registrationToUser(RegistrationDto registrationDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(registrationDto, User.class);
    }
}
