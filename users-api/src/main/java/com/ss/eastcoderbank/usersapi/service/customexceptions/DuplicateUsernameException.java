package com.ss.eastcoderbank.usersapi.service.customexceptions;


public class DuplicateUsernameException extends DuplicateConstraintsException {
    public DuplicateUsernameException() {
        super(ExceptionMessages.USERNAME);
    }


    public DuplicateUsernameException(String message) {
        super(message);
    }
}
