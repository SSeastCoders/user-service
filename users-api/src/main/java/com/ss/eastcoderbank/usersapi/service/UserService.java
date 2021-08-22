package com.ss.eastcoderbank.usersapi.service;

import com.ss.eastcoderbank.core.constraints.UserConstraints;
import com.ss.eastcoderbank.core.exeception.UserNotFoundException;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.model.user.UserRole;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.core.repository.UserRoleRepository;
import com.ss.eastcoderbank.core.transferdto.UserDto;
import com.ss.eastcoderbank.core.transfermapper.UserMapper;
import com.ss.eastcoderbank.usersapi.dto.CreateUserDto;
import com.ss.eastcoderbank.usersapi.dto.UpdateProfileDto;
import com.ss.eastcoderbank.usersapi.mapper.CreateUserMapper;
import com.ss.eastcoderbank.usersapi.mapper.UpdateProfileMapper;
import com.ss.eastcoderbank.usersapi.service.CustomExceptions.DuplicateConstraintsException;
import com.ss.eastcoderbank.usersapi.service.CustomExceptions.DuplicateEmailException;
import com.ss.eastcoderbank.usersapi.service.CustomExceptions.DuplicateUsernameException;
import com.ss.eastcoderbank.usersapi.service.CustomExceptions.ExceptionMessages;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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



    public Page<UserDto> getUsersByRole(String title, Pageable page) {
        return userRepository.findUserByRoleTitle(title, page).map(user -> userMapper.mapToDto(user));

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
            log.info(e.getMessage(), e);
            throw e; // something went wrong.
        }
    }


    public Integer updateUser(UpdateProfileDto updateProfileDto, Integer id) {
        try {
            User user = userRepository.getById(id);
            updateProfileMapper.updateEntity(updateProfileDto, user, passwordEncoder);
            userRoleRepository.findUserRoleByTitle(updateProfileDto.getRole()).ifPresent(user::setRole);
            userRepository.save(user);
            return user.getId();
        } catch (EntityNotFoundException e) {
            throw new UserNotFoundException();
        } catch (DataIntegrityViolationException dive) {
            Throwable thr = dive.getCause();
            if (thr instanceof ConstraintViolationException) {
                handleUniqueConstraints(((ConstraintViolationException) thr).getConstraintName());
            }
            log.info(dive.getMessage(), dive);
            throw dive;
        }
    }

    public Page<UserDto> getUsers(Integer pageNumber, Integer pageSize) {
        return userRepository.findAll(PageRequest.of(pageNumber, pageSize)).map(user -> userMapper.mapToDto(user));
    }

    public Page<UserDto> getSortedUsers(Integer pageNumber, Integer pageSize, boolean asc, String sort) {
        //resultPage = userService.findPaginated(PageRequest.of(page, size, Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sort)), search);
        return userRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sort))).map(user -> userMapper.mapToDto(user));
    }
   // PageRequest.of(0, 3, Sort.by("price").descending());

    public UserDto getUserById(Integer id) {
        return userMapper.mapToDto(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public List<UserRole> getRoles() {
        return userRoleRepository.findAll();
    }


    protected void handleUniqueConstraints(String constraint) {

        String constraintLower = constraint.toLowerCase();
        if (constraintLower.contains(UserConstraints.EMAILANDUSERNAME))
            throw new DuplicateConstraintsException(ExceptionMessages.USERNAMEANDEMAIL);
        else if (constraintLower.contains(UserConstraints.EMAIL))
            throw new DuplicateEmailException(ExceptionMessages.EMAIL);
        else if (constraintLower.contains(UserConstraints.USERNAME))
            throw new DuplicateUsernameException(ExceptionMessages.USERNAME);

    }


    public Integer deactivateUser(Integer id) {

        Optional<User> deactivatedUser = userRepository.findById(id);

        User user = deactivatedUser.orElseThrow(UserNotFoundException::new);
        if (!user.getActiveStatus()) throw new UserNotFoundException();
        user.setActiveStatus(false);
        userRepository.save(user);
        return user.getId();
    }


}

