package com.ss.eastcoderbank.userservice.security;

import com.auth0.jwt.JWT;

public class JwtParser {
    public static Integer parseId(String token) {
        return Integer.valueOf(JWT.decode(token).getSubject());
    }
}
