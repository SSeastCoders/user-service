package com.ss.eastcoderbank.usersapi.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtAuthenticationFilterTest {

    private AuthenticationManager manager;

    @Test
    public void testConstructor() {
        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        JwtAuthenticationFilter actualJwtAuthenticationFilter = new JwtAuthenticationFilter(
                new ProviderManager(authenticationProviderList), "Jwt Secret");

        assertEquals("username", actualJwtAuthenticationFilter.getUsernameParameter());
        assertTrue(actualJwtAuthenticationFilter
                .getRememberMeServices() instanceof org.springframework.security.web.authentication.NullRememberMeServices);
        assertEquals("password", actualJwtAuthenticationFilter.getPasswordParameter());
    }

    @Test
    public void testConstructor2() {
        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        JwtAuthenticationFilter actualJwtAuthenticationFilter = new JwtAuthenticationFilter(
                new ProviderManager(authenticationProviderList), "Jwt Secret");

        assertEquals("username", actualJwtAuthenticationFilter.getUsernameParameter());
        assertTrue(actualJwtAuthenticationFilter
                .getRememberMeServices() instanceof org.springframework.security.web.authentication.NullRememberMeServices);
        assertEquals("password", actualJwtAuthenticationFilter.getPasswordParameter());
    }

    @Test
    public void testStartupDetectsMissingSecret() {
        assertThatIllegalArgumentException().isThrownBy(() -> new BasicAuthenticationFilter(this.manager, null));
    }

    @Test
    public void testStartupDetectsMissingAuthenticationManager() {
        assertThatIllegalArgumentException().isThrownBy(() -> new BasicAuthenticationFilter(null));
    }

}

