package com.ss.eastcoderbank.userservice.service;

import com.ss.eastcoderbank.userservice.dto.UserDTO;
import com.ss.eastcoderbank.userservice.exceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.userservice.exceptions.DuplicateEmailException;
import com.ss.eastcoderbank.userservice.exceptions.DuplicatePhoneException;
import com.ss.eastcoderbank.userservice.exceptions.DuplicateUsernameException;
import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import com.ss.eastcoderbank.userservice.repository.UserRoleRepository;
import com.ss.eastcoderbank.userservice.service.constraints.Constraint;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

//import javax.validation.ConstraintViolationException;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepo;
    @Autowired
    private final UserRoleRepository userRoleRepo;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private final ModelMapper modelMapper;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }


    public Integer manuallyCreateUser(UserDTO userDTO) throws DuplicateEmailException, DuplicateUsernameException, DuplicatePhoneException {

        User user = userDTOToUser(userDTO);
        //        sets user's address
        Address address = new Address();
        address.setStreetAddress(userDTO.getAddress().getStreetAddress());
        address.setCity(userDTO.getAddress().getCity());
        address.setState(userDTO.getAddress().getState());
        address.setZip(userDTO.getAddress().getZip());
        user.setAddress(address);
        //        sets user's credential
        Credential credential = new Credential();
        credential.setUsername(userDTO.getCredential().getUsername());
        credential.setPassword(userDTO.getCredential().getPassword());
        user.setCredential(credential);

        UserRole role = userRoleRepo.findUserRoleByTitle("administrator").orElse(new UserRole("administrator"));

        user.setRole(role);
        user.setDataJoined(LocalDate.now());

        String encodedPassword = bCryptPasswordEncoder.encode(user.getCredential().getPassword());
        user.getCredential().setPassword(encodedPassword);

        try {
             userRepo.saveAndFlush(user);
        }catch (DataIntegrityViolationException dive) {
            log.error(dive.getMessage());
            Throwable thr = dive.getCause();
            if (thr instanceof ConstraintViolationException) {
                handleUniqueConstraints(((ConstraintViolationException) thr).getConstraintName());
            }
            throw dive;
        }
        return user.getId();
    }

    User userDTOToUser(UserDTO userDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(userDTO, User.class);
    }

    private void handleUniqueConstraints(String constraint) {
        String constraintLower = constraint.toLowerCase();
        if (constraintLower.contains(Constraint.EMAILANDUSERNAME)) throw new DuplicateConstraintsException("duplicate username and email");
        else if (constraintLower.contains(Constraint.EMAIL)) throw new DuplicateEmailException("duplicate email");
        else if (constraintLower.contains(Constraint.USERNAME)) throw new DuplicateUsernameException("duplicate username");
        else if (constraintLower.contains(Constraint.PHONE)) throw new DuplicatePhoneException("Phone number already associated with a registered user!");
    }


}



