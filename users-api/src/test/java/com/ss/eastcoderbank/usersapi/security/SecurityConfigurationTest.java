package com.ss.eastcoderbank.usersapi.security;

import com.ss.eastcoderbank.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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
    }

    @Test
    void testPasswordEncoder() {
        // TODO: This test is incomplete.
        //   Reason: Nothing to assert: neither the return value type of the method under test nor the types of its parameters have observers (e.g. getters or public fields).
        //   Add observers (e.g. getters or public fields) to the declaring class
        //   of the method under test, its return type or any of its parameter
        //   types.
        //   See https://diff.blue/R003

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        SecurityConfiguration securityConfiguration = new SecurityConfiguration(userRepository,
                new UserPrincipalService(mock(UserRepository.class)), "Jwt Secret");

        // Act
        securityConfiguration.passwordEncoder();
    }
}

