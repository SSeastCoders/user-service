package com.ss.eastcoderbank.userservice.exceptions;


public class DuplicatePhoneException extends RuntimeException {
    public DuplicatePhoneException(String message) {
        super(message);
    }
}