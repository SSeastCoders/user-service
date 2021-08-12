package com.ss.eastcoderbank.usersapi.service.CustomExceptions;


public class DuplicateUsernameException extends DuplicateConstraintsException {
    public DuplicateUsernameException() {
        super(ExceptionMessages.USERNAME);
    }


    public DuplicateUsernameException(String message) {
        super(message);
    }
}
