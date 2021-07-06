package com.ss.eastcoderbank.userservice.service.CustomExceptions;

public class DuplicateEmailException extends DuplicateConstraintsException{
    public DuplicateEmailException(String message) {
        super(message);
    }
}
