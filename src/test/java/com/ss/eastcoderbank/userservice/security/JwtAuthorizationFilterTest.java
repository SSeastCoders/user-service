package com.ss.eastcoderbank.userservice.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.ss.eastcoderbank.userservice.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

public class JwtAuthorizationFilterTest {
    @Test
    public void testConstructor() {
        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        ProviderManager providerManager = new ProviderManager(authenticationProviderList);
        JwtAuthorizationFilter actualJwtAuthorizationFilter = new JwtAuthorizationFilter(providerManager,
                mock(UserRepository.class), "Jwt Secret");

        assertTrue(actualJwtAuthorizationFilter
                .getEnvironment() instanceof org.springframework.web.context.support.StandardServletEnvironment);
        assertNull(actualJwtAuthorizationFilter.getFilterConfig());
        List<AuthenticationProvider> providers = providerManager.getProviders();
        assertSame(authenticationProviderList, providers);
        assertEquals(1, providers.size());
        assertTrue(providerManager.isEraseCredentialsAfterAuthentication());
    }

    @Test
    public void testConstructor2() {
        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        ProviderManager providerManager = new ProviderManager(authenticationProviderList);
        JwtAuthorizationFilter actualJwtAuthorizationFilter = new JwtAuthorizationFilter(providerManager,
                new Http403ForbiddenEntryPoint(), mock(UserRepository.class), "Jwt Secret");

        assertTrue(actualJwtAuthorizationFilter
                .getEnvironment() instanceof org.springframework.web.context.support.StandardServletEnvironment);
        assertNull(actualJwtAuthorizationFilter.getFilterConfig());
        List<AuthenticationProvider> providers = providerManager.getProviders();
        assertSame(authenticationProviderList, providers);
        assertEquals(1, providers.size());
        assertTrue(providerManager.isEraseCredentialsAfterAuthentication());
    }

    @Test
    public void testDoFilterInternal() throws IOException, ServletException {
        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(
                new ProviderManager(authenticationProviderList), mock(UserRepository.class), "Jwt Secret");
        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((javax.servlet.ServletRequest) any(), (javax.servlet.ServletResponse) any());
        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter((javax.servlet.ServletRequest) any(), (javax.servlet.ServletResponse) any());
    }
}

