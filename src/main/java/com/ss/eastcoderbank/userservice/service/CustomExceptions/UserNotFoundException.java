package com.ss.eastcoderbank.userservice.service.CustomExceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("No user found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
