package com.ss.eastcoderbank.userservice.security;

import com.auth0.jwt.JWT;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final String jwtSecret;
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint, UserRepository userRepository, @Value("${jwt.secret}") String jwtSecret) {
        super(authenticationManager, authenticationEntryPoint);
        this.userRepository = userRepository;
        this.jwtSecret = jwtSecret;
    }

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, @Value("${jwt.secret}") String jwtSecret) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtSecret = jwtSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Read the Authorization header, where the JWT token should be
        String header = request.getHeader(JwtUtil.JWT_UTIL.getHeader());

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtUtil.JWT_UTIL.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtUtil.JWT_UTIL.getHeader())
                .replace(JwtUtil.JWT_UTIL.getTokenPrefix(), "");

        if (token != null) {
            // parse the token and validate it
            String userName = JWT.require(HMAC512(jwtSecret.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();

            // Search in the DB if we find the user by token subject (username)
            // If so, then grab user details and create spring auth token using username, pass, authorities/roles
            if (userName != null) {
                try {
                    User user = userRepository.findById(Integer.valueOf(userName)).orElseThrow(RuntimeException::new);
                    UserPrincipal principal = new UserPrincipal(user);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getId(), null, principal.getAuthorities());
                    return auth;
                } catch (UsernameNotFoundException e) {
                    return null;
                }
            }
            return null;
        }
        return null;
    }

}
