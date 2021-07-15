package com.ss.eastcoderbank.userservice.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.eastcoderbank.userservice.dto.LoginDto;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //@Value("${jwt.secret}")
    private final String jwtSecret;
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    //@Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, @Value("${jwt.secret}") String jwtSecret) {
        this.authenticationManager = authenticationManager;
        this.jwtSecret = jwtSecret;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        LoginDto credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
            //e.printStackTrace();
        }

        // Create login token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>());

        // Authenticate user
        Authentication auth = authenticationManager.authenticate(authenticationToken);

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
        // Get principal
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
    }
}
