package com.ss.eastcoderbank.userservice.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.TestingAuthenticationToken;

public class JwtAuthenticationFilterTest {
    @Test
    public void testConstructor() {
        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        JwtAuthenticationFilter actualJwtAuthenticationFilter = new JwtAuthenticationFilter(
                new ProviderManager(authenticationProviderList));
        assertEquals("username", actualJwtAuthenticationFilter.getUsernameParameter());
        assertTrue(actualJwtAuthenticationFilter
                .getRememberMeServices() instanceof org.springframework.security.web.authentication.NullRememberMeServices);
        assertEquals("password", actualJwtAuthenticationFilter.getPasswordParameter());
    }

    @Test
    public void testSuccessfulAuthentication() throws IOException, ServletException {
        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
                new ProviderManager(authenticationProviderList));
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);
        UserPrincipal userPrincipal = mock(UserPrincipal.class);
        when(userPrincipal.getUsername()).thenReturn("foo");
        jwtAuthenticationFilter.successfulAuthentication(request, response, chain,
                new TestingAuthenticationToken(userPrincipal, "Credentials"));
        verify(userPrincipal).getUsername();
    }
}

