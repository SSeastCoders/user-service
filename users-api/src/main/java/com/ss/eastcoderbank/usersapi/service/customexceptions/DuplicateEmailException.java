package com.ss.eastcoderbank.usersapi.service.customexceptions;


public class DuplicateEmailException extends DuplicateConstraintsException{

    public DuplicateEmailException(String message) {
        super(message);
    }
}
