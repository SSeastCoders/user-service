package com.ss.eastcoderbank.userservice.service.CustomExceptions;

public class DuplicateUsernameException extends DuplicateConstraintsException {
    public DuplicateUsernameException(String message) {
        super(message);
    }
}
