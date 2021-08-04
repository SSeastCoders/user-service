package com.ss.eastcoderbank.usersapi.security;

import com.ss.eastcoderbank.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

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

}

