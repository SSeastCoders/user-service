package com.ss.eastcoderbank.usersapi.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ss.eastcoderbank.core.model.user.Address;
import com.ss.eastcoderbank.core.model.user.Credential;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.model.user.UserRole;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.core.repository.UserRoleRepository;
import com.ss.eastcoderbank.usersapi.dto.LoginDto;
import com.ss.eastcoderbank.usersapi.mapper.LoginMapper;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthorizationService.class})
@ExtendWith(SpringExtension.class)
public class AuthorizationServiceTest {
    @Autowired
    private AuthorizationService authorizationService;

    @MockBean
    private LoginMapper loginMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserRoleRepository userRoleRepository;

    @Test
    public void testUserLogin() throws Exception {
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

        // Act
        this.authorizationService.userLogin(loginDto);

        // Assert
        verify(this.loginMapper).mapToEntity((LoginDto) any());
    }
}

