package com.ss.eastcoderbank.userservice.service;

import com.ss.eastcoderbank.userservice.dto.CreateUserDto;
import com.ss.eastcoderbank.userservice.dto.UpdateProfileDto;
import com.ss.eastcoderbank.userservice.dto.UserDto;
import com.ss.eastcoderbank.userservice.mapper.CreateUserMapper;
import com.ss.eastcoderbank.userservice.mapper.UpdateProfileMapper;
import com.ss.eastcoderbank.userservice.mapper.UserMapper;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import com.ss.eastcoderbank.userservice.repository.UserRoleRepository;
import com.ss.eastcoderbank.userservice.service.CustomExceptions.*;
import com.ss.eastcoderbank.userservice.service.constraints.DbConstraints;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import javax.validation.ConstraintViolationException;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private UpdateProfileMapper updateProfileMapper;
    private CreateUserMapper createUserMapper;

    public List<UserDto> getUsersByRole(String title) {
        List<User> users = userRepository.findUserByRoleTitle(title);
        return users.stream().map(userMapper::mapToDto).collect(Collectors.toList());
    }

    public Integer createUser(CreateUserDto createUserDto) throws DuplicateConstraintsException {
        createUserDto.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        User user = createUserMapper.mapToEntity(createUserDto);
        UserRole role = userRoleRepository.findUserRoleByTitle(createUserDto.getRole()).orElse(userRoleRepository.getById(2));
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


    // TODO implement proper transaction as there are multiple reads
    @Transactional(propagation = Propagation.NESTED, isolation = Isolation.READ_COMMITTED)
    public Integer updateUser(UpdateProfileDto updateProfileDto, Integer id) {
        if (updateProfileDto.getPassword() != null) {
            updateProfileDto.setPassword(passwordEncoder.encode(updateProfileDto.getPassword()));
        }
        Optional<User> savedUser = userRepository.findById(id);

        User user = savedUser.orElseThrow(UserNotFoundException::new);
        User convertedUser = updateProfileMapper.mapToEntity(updateProfileDto);

        if (convertedUser.getFirstName() != null) {
            user.setFirstName(convertedUser.getFirstName());
        }
        if (convertedUser.getRole() != null && convertedUser.getRole().getTitle() != null) {
            userRoleRepository.findUserRoleByTitle(convertedUser.getRole().getTitle()).ifPresent(user::setRole);
        }
        if (convertedUser.getLastName() != null ) {
            user.setLastName(convertedUser.getLastName());
        }
        if (convertedUser.getAddress() != null ) {
            user.setAddress(convertedUser.getAddress());
        }
        if (convertedUser.getCredential() != null ) {
            if (convertedUser.getCredential().getUsername() != null)
                user.getCredential().setUsername(convertedUser.getCredential().getUsername());
            if (convertedUser.getCredential().getPassword() != null)
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

        return user.getId();
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(user -> userMapper.mapToDto(user)).collect(Collectors.toList());
    }


    public UserDto getUserById(Integer id) {
        return userMapper.mapToDto(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public List<UserRole> getRoles() {
        return userRoleRepository.findAll();
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


    public Integer deactivateUser(Integer id) {

        Optional<User> deactivatedUser = userRepository.findById(id);

        User user = deactivatedUser.orElseThrow(UserNotFoundException::new);
        if (!user.isActiveStatus()) throw new UserNotFoundException();
        user.setActiveStatus(false);
        userRepository.save(user);
        return user.getId();
    }

    public Page<User> findPaginated(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return this.userRepository.findAll(pageable);
    }

}

