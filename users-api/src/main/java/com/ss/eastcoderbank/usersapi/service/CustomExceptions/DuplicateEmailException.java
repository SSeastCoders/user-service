package com.ss.eastcoderbank.usersapi.service.CustomExceptions;


public class DuplicateEmailException extends DuplicateConstraintsException{

    public DuplicateEmailException() {
        super(ExceptionMessages.EMAIL);
    }


    public DuplicateEmailException(String message) {
        super(message);
    }
}
