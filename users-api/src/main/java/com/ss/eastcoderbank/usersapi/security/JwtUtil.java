package com.ss.eastcoderbank.usersapi.security;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum JwtUtil {

    JWT_UTIL(864000000, "Bearer ", "Authorization");

    private final Integer expirationTime;
    private final String tokenPrefix;
    private final String header;

    JwtUtil(Integer expirationTime, String tokenPrefix, String header) {
        this.expirationTime = expirationTime;
        this.tokenPrefix = tokenPrefix;
        this.header = header;
    }
}