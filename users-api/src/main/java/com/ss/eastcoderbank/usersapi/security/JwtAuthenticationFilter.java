package com.ss.eastcoderbank.usersapi.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.usersapi.dto.LoginDto;
import com.ss.eastcoderbank.usersapi.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //@Value("${jwt.secret}")
    private final String jwtSecret;
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, @Value("${jwt.secret}") String jwtSecret) {
        this.authenticationManager = authenticationManager;
        this.jwtSecret = jwtSecret;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginDto credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
        } catch (IOException | NullPointerException e) {
            logger.error(e.getMessage());
        }

        // Create login token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>());

        // Authenticate user
        try {
           return authenticationManager.authenticate(authenticationToken);
        } catch(NullPointerException e){
            throw new BadCredentialsException("Bad Credentials");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
        // Get principal
        try {
            UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();

            // Create JWT Token
            String token = JWT.create()

                .withSubject(String.valueOf(principal.getUser().getId()))
                .withClaim("username", principal.getUsername())
                .withClaim("role", principal.getUser().getRole().getTitle())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.JWT_UTIL.getExpirationTime()))
                .sign(HMAC512(jwtSecret.getBytes()));

            // Add token in response
            response.addHeader(JwtUtil.JWT_UTIL.getHeader(), JwtUtil.JWT_UTIL.getTokenPrefix() + token);
            response.addHeader("id", String.valueOf(principal.getUser().getId()));
        } catch (UsernameNotFoundException e){
            logger.error("invalid login");
        }
    }
}
