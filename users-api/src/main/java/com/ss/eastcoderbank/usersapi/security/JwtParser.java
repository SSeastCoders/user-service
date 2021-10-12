package com.ss.eastcoderbank.usersapi.security;

import com.auth0.jwt.JWT;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtParser {
    public static Integer parseId(String token) {
        return Integer.valueOf(JWT.decode(token).getSubject());
    }
}
