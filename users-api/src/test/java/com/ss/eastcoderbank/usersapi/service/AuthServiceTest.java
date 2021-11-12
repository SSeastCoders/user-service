package com.ss.eastcoderbank.usersapi.service;

import com.ss.eastcoderbank.core.model.user.Address;
import com.ss.eastcoderbank.core.model.user.Credential;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.model.user.UserRole;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.core.repository.UserRoleRepository;
import com.ss.eastcoderbank.usersapi.dto.LoginDto;
import com.ss.eastcoderbank.usersapi.mapper.LoginMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ContextConfiguration(classes = {AuthService.class})
@ExtendWith(SpringExtension.class)
class AuthServiceTest {
    @Autowired
    private AuthService authorizationService;

    @MockBean
    private LoginMapper loginMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserRoleRepository userRoleRepository;

    private User fakeUser() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);
        when(this.loginMapper.mapToEntity((LoginDto) any())).thenReturn(user);

        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("iloveyou");
        loginDto.setUsername("janedoe");

        return user;
    }
    @Test
    void testUserLogin() throws Exception {
        // Arrange
        User user = fakeUser();
        when(this.loginMapper.mapToEntity((LoginDto) any())).thenReturn(user);

        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("iloveyou");
        loginDto.setUsername("janedoe");

        // Act
        this.authorizationService.userLogin(loginDto);

        // Assert
        verify(this.loginMapper).mapToEntity((LoginDto) any());
    }

    @Test
    void testFindUser()  {
        User user = fakeUser();
        userRepository.save(user);
        String username = fakeUser().getCredential().getUsername();

        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(user.getRole().getTitle());
        authorities.add(role);
        when(userRepository.findByCredentialUsername(username)).thenReturn(user);
        assertSame(user, userRepository.findByCredentialUsername(username));
        assertFalse(authorities.isEmpty());
    }

    @Test
    void testFindUserFP()  {
        String username = fakeUser().getCredential().getUsername();

        when(userRepository.findByCredentialUsername(null)).thenThrow(new UsernameNotFoundException(username));

        assertThrows(UsernameNotFoundException.class, ()->  userRepository.findByCredentialUsername(null));
    }


}

