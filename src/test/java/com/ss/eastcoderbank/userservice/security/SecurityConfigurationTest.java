package com.ss.eastcoderbank.userservice.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.ss.eastcoderbank.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

public class SecurityConfigurationTest {
    @Test
    public void testAuthenticationProvider() {
        UserRepository userRepository = mock(UserRepository.class);
        DaoAuthenticationProvider actualAuthenticationProviderResult = (new SecurityConfiguration(userRepository,
                new UserPrincipalService(mock(UserRepository.class)), "Jwt Secret")).authenticationProvider();
        assertTrue(actualAuthenticationProviderResult
                .getUserCache() instanceof org.springframework.security.core.userdetails.cache.NullUserCache);
        assertTrue(actualAuthenticationProviderResult.isHideUserNotFoundExceptions());
        assertFalse(actualAuthenticationProviderResult.isForcePrincipalAsString());
    }

    @Test
    public void testPasswordEncoder() {
        // TODO: This test is incomplete.
        //   Reason: Nothing to assert: neither the return value type of the method under test nor the types of its parameters have observers (e.g. getters or public fields).
        //   Add observers (e.g. getters or public fields) to the declaring class
        //   of the method under test, its return type or any of its parameter
        //   types.
        //   See https://diff.blue/R003

        UserRepository userRepository = mock(UserRepository.class);
        (new SecurityConfiguration(userRepository, new UserPrincipalService(mock(UserRepository.class)), "Jwt Secret"))
                .passwordEncoder();
    }
}

