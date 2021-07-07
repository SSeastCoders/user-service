package com.ss.eastcoderbank.userservice.exceptions;

public class DuplicateUsernameException extends Exception {
    public DuplicateUsernameException(String message) {
        super(message);
    }
}
