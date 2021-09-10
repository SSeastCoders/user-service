package com.ss.eastcoderbank.usersapi.security;

import com.ss.eastcoderbank.core.repository.UserRepository;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

