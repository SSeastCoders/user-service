package com.ss.eastcoderbank.userservice.service;

import com.ss.eastcoderbank.userservice.dto.RegistrationDto;
import com.ss.eastcoderbank.userservice.dto.UpdateProfileDto;
import com.ss.eastcoderbank.userservice.dto.UserDto;
import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import com.ss.eastcoderbank.userservice.repository.UserRoleRepository;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateEmailException;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.DuplicateUsernameException;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.ExceptionMessages;

import com.ss.eastcoderbank.userservice.service.constraints.DbConstraints;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//import javax.validation.ConstraintViolationException;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public List<User> getAllAdmins() {
        return userRepository.findUserByRoleId(1);
    }

    public Integer userRegistration(RegistrationDto registrationDto) throws DuplicateConstraintsException {
        //credCust.setPassword(passwordEncoder.encode("customer"));
        registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        User user = registrationToUser(registrationDto);
        UserRole role = userRoleRepository.findUserRoleByTitle("Customer").orElse(new UserRole("Customer"));
        user.setRole(role);
        user.setDateJoined(LocalDate.now());

        // ADDED TO ENSURE LOGIN
        user.setActiveStatus(true);
        try {
            userRepository.save(user);
            return user.getId();
        } catch (DataIntegrityViolationException e) {
            Throwable t = e.getCause();
            if (t instanceof ConstraintViolationException) {
                handleUniqueConstraints(((ConstraintViolationException) t).getConstraintName());
            }
            throw e; // something went wrong.
        }
    }

    public Integer updateUser(UpdateProfileDto updateProfileDto, Integer id) {
        if (updateProfileDto.getPassword() != null) {
            updateProfileDto.setPassword(passwordEncoder.encode(updateProfileDto.getPassword()));
        }
        Optional<User> savedUser = userRepository.findById(id);

        User convertedUser = updateDtoToUser(updateProfileDto);
        User user = null;

        if (savedUser.isPresent()) {
            user = savedUser.get();

            if (convertedUser.getFirstName() != null) {
                user.setFirstName(convertedUser.getFirstName());
            }
            if (convertedUser.getLastName() != null ) {
                user.setLastName(convertedUser.getLastName());
            }
            if (convertedUser.getAddress() != null ) {
                user.setAddress(convertedUser.getAddress());
            }
            if (convertedUser.getCredential() != null ) {
                user.getCredential().setPassword(convertedUser.getCredential().getPassword());
            }
            if (convertedUser.getDateJoined() != null ) {
                user.setDateJoined(convertedUser.getDateJoined());
            }
            if (user.isActiveStatus() != convertedUser.isActiveStatus()) {
                user.setActiveStatus(convertedUser.isActiveStatus());
            }
            if (convertedUser.getDob() != null ) {
                user.setDob(convertedUser.getDob());
            }

            if (convertedUser.getEmail() != null ) {
                user.setEmail(convertedUser.getEmail());
            }
            if (convertedUser.getPhone() != null ) {
                user.setPhone(convertedUser.getPhone());
            }
            try {
                userRepository.save(user);
            } catch (DataIntegrityViolationException dive) {
                Throwable thr = dive.getCause();
                if (thr instanceof ConstraintViolationException) {
                    handleUniqueConstraints(((ConstraintViolationException) thr).getConstraintName());
                }
                throw dive;
            }

        } else {
            throw new UsernameNotFoundException("User not found");
        }
        return user.getId();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }


    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<UserRole> getRoles() {
        return userRoleRepository.findAll();
    }

    protected User convertToEntity(UserDto userDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(userDto, User.class);
    }

    //Needs to be tested
    protected User registrationToUser(RegistrationDto registrationDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        User user = modelMapper.map(registrationDto, User.class);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return user;
    }
    public Integer manuallyCreateUser(UserDto userDTO) {

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

        UserRole role = userRoleRepository.findUserRoleByTitle("Administrator").orElse(new UserRole("Administrator"));

        user.setRole(role);
        user.setDateJoined(LocalDate.now());

        String encodedPassword = passwordEncoder.encode(user.getCredential().getPassword());
        user.getCredential().setPassword(encodedPassword);

        // ADDED TO ENSURE LOGIN
        user.setActiveStatus(true);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException dive) {
            log.error(dive.getMessage());
            Throwable thr = dive.getCause();
            if (thr instanceof ConstraintViolationException) {
                handleUniqueConstraints(((ConstraintViolationException) thr).getConstraintName());
            }
            throw dive;
        }
        return user.getId();
    }

    public User userDTOToUser(UserDto userDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userDTO, User.class);
    }

    public User updateDtoToUser(UpdateProfileDto updateProfileDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(updateProfileDto, User.class);
    }

    protected void handleUniqueConstraints(String constraint) {

        String constraintLower = constraint.toLowerCase();
        if (constraintLower.contains(DbConstraints.EMAILANDUSERNAME))
            throw new DuplicateConstraintsException(ExceptionMessages.USERNAMEANDEMAIL);
        else if (constraintLower.contains(DbConstraints.EMAIL))
            throw new DuplicateEmailException(ExceptionMessages.EMAIL);
        else if (constraintLower.contains(DbConstraints.USERNAME))
            throw new DuplicateUsernameException(ExceptionMessages.USERNAME);

    }







    public Integer updateUserDetails(UserDto userDTO) {
        userDTO.getCredential().setPassword(passwordEncoder.encode(userDTO.getCredential().getPassword()));
        Optional<User> savedUser = userRepository.findById(userDTO.getId());

        User user = null;

        if(savedUser.isPresent()){
            user = savedUser.get();

            if(!user.getFirstName().equals(userDTO.getFirstName()) && userDTO.getFirstName() != null){
                user.setFirstName(userDTO.getFirstName());
            }
            if(!user.getLastName().equals(userDTO.getLastName()) && userDTO.getLastName() != null){
                user.setLastName(userDTO.getLastName());
            }
            if(!user.getAddress().equals(userDTO.getAddress()) && userDTO.getAddress() != null){
                user.setAddress(userDTO.getAddress());
            }
            if(!user.getCredential().equals(userDTO.getCredential()) && userDTO.getCredential() != null){
                user.setCredential(userDTO.getCredential());
            }
            if(!user.getDateJoined().equals(userDTO.getDateJoined()) && userDTO.getDateJoined() != null){
                user.setDateJoined(userDTO.getDateJoined());
            }
            if(user.isActiveStatus() != userDTO.isActiveStatus()) {
                user.setActiveStatus(userDTO.isActiveStatus());
            }
            if(!user.getDob().equals(userDTO.getDob()) && userDTO.getDob() != null){
                user.setDob(userDTO.getDob());
            }

            if(!user.getEmail().equals(userDTO.getEmail()) && userDTO.getEmail() != null){
                user.setEmail(userDTO.getEmail());
            }
            if(!user.getPhone().equals(userDTO.getPhone()) && userDTO.getPhone() != null){
                user.setPhone(userDTO.getPhone());
            }
            try {
                userRepository.saveAndFlush(user);
            } catch(DataIntegrityViolationException dive) {
                log.error(dive.getMessage());
                Throwable thr = dive.getCause();
                if (thr instanceof ConstraintViolationException) {
                    handleUniqueConstraints(((ConstraintViolationException) thr).getConstraintName());
                }
                throw dive;
            }

        } else {
            user = new User();
        }
        return user.getId();

    }

    public Integer deactivateUser(UserDto userDTO) {

        Optional<User> deactivatedUser = userRepository.findById(userDTO.getId());

        User user = null;

        if(deactivatedUser.isPresent()) {
            user = deactivatedUser.get();

            user.setActiveStatus(false);


            userRepository.saveAndFlush(user);
        }
        return user.getId();
    }


}

