package com.ss.eastcoderbank.usersapi.service.customexceptions;


public class DuplicateUsernameException extends DuplicateConstraintsException {


    public DuplicateUsernameException(String message) {
        super(message);
    }
}
