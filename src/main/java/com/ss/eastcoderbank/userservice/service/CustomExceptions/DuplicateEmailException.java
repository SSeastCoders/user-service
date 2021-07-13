package com.ss.eastcoderbank.userservice.service.CustomExceptions;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super(ExceptionMessages.EMAIL);
    }


    public DuplicateEmailException(String message) {
        super(message);
    }
}
