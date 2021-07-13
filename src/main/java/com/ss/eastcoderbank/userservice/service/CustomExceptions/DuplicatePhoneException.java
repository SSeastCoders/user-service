package com.ss.eastcoderbank.userservice.service.CustomExceptions;


public class DuplicatePhoneException extends RuntimeException {
    public DuplicatePhoneException() {
        super(ExceptionMessages.PHONE);
    }

    public DuplicatePhoneException(String message) {
        super(message);
    }
}