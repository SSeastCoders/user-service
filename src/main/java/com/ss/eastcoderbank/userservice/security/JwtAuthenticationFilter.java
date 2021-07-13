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

import javax.naming.directory.InvalidAttributeIdentifierException;
import javax.persistence.Access;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    //@Value("${jwt.secret}")
    private final String jwtSecret;

    //@Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, @Value("${jwt.secret}") String jwtSecret){
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

                .withSubject(String.valueOf(principal.getId()))
                .withClaim("username", principal.getUsername())
                .withClaim("role", principal.getRole().getTitle())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.JWT_UTIL.getExpirationTime()))
                .sign(HMAC512(jwtSecret.getBytes()));

        // Add token in response
        response.addHeader(JwtUtil.JWT_UTIL.getHeader(), JwtUtil.JWT_UTIL.getTokenPrefix() + token);
    }

    //rename exception hazel
    public Optional<User> parseJWTToken(String jwtToken) throws InvalidAttributeIdentifierException{

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .parseClaimsJws(jwtToken).getBody();

        //return new UserPrincipal(userRepository.findById(Integer.valueOf(claims.getSubject())).orElseThrow(InvalidAttributeIdentifierException::new));
        return userRepository.findById(Integer.valueOf(claims.getSubject()));

        //String[] jwtChunks = jwtToken.split("\\.");
        //Base64.Decoder decoder = Base64.getDecoder();

        //JWT.decode(jwtToken);

        //String header = new String(jwtChunks[0]);
        //String payload = new String(jwtChunks[1]);

        //JWT.decode(jwtToken);


    }
}
