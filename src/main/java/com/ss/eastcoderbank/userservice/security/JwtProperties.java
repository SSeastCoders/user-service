package com.ss.eastcoderbank.userservice.security;

public class JwtProperties {

    public static final String SECRET = "EastCodersBank123";

    public static final int EXPIRATION_TIME = 864000000; //10 years...

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

}
