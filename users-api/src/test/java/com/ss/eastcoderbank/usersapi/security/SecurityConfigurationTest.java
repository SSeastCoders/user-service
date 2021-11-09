package com.ss.eastcoderbank.usersapi.security;

import com.ss.eastcoderbank.core.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigurationTest {
    @Test
    void testAuthenticationProvider() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        SecurityConfiguration securityConfiguration = new SecurityConfiguration(userRepository,
                new UserPrincipalService(mock(UserRepository.class)), "Jwt Secret");

        // Act
        DaoAuthenticationProvider actualAuthenticationProviderResult = securityConfiguration.authenticationProvider();

        // Assert
        assertTrue(actualAuthenticationProviderResult
                .getUserCache() instanceof org.springframework.security.core.userdetails.cache.NullUserCache);
        assertTrue(actualAuthenticationProviderResult.isHideUserNotFoundExceptions());
        assertFalse(actualAuthenticationProviderResult.isForcePrincipalAsString());
    }

    @Test
    void testCorsConfigurationSource() {

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        SecurityConfiguration securityConfiguration = new SecurityConfiguration(userRepository,
                new UserPrincipalService(mock(UserRepository.class)), "Jwt Secret");

        // Act
        CorsConfigurationSource actualCorsConfigurationSourceResult = securityConfiguration.corsConfigurationSource();
        MockHttpServletRequest param0 = new MockHttpServletRequest();
        actualCorsConfigurationSourceResult.getCorsConfiguration(param0);

        // Assert
        assertNotNull(userRepository);
        assertNotNull(securityConfiguration);
        assertNotNull(actualCorsConfigurationSourceResult);
        assertNotNull(param0);
    }

    @Test
    void testCorsConfigurationSource2() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        SecurityConfiguration securityConfiguration = new SecurityConfiguration(userRepository,
                new UserPrincipalService(mock(UserRepository.class)), "Jwt Secret");

        // Act
        CorsConfigurationSource actualCorsConfigurationSourceResult = securityConfiguration.corsConfigurationSource();
        MockHttpServletRequest param0 = new MockHttpServletRequest("https://example.org/example",
                "https://example.org/example");

        CorsConfiguration actualCorsConfiguration = actualCorsConfigurationSourceResult.getCorsConfiguration(param0);

        // Assert
        assertNull(actualCorsConfiguration);
        assertNotNull(userRepository);
        assertNotNull(securityConfiguration);
        assertNotNull(actualCorsConfigurationSourceResult);
        assertNotNull(param0);
    }

    @Test
    void testPasswordEncoder() {

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        SecurityConfiguration securityConfiguration = new SecurityConfiguration(userRepository,
                new UserPrincipalService(mock(UserRepository.class)), "Jwt Secret");

        // Act
        PasswordEncoder passwordencoder = securityConfiguration.passwordEncoder();


        //Assert
        assertNotNull(passwordencoder);
    }
}

