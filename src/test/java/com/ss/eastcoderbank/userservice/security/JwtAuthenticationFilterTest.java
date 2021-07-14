package com.ss.eastcoderbank.userservice.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

public class JwtAuthenticationFilterTest {
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
}

