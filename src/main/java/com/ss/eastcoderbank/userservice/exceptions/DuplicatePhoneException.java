package com.ss.eastcoderbank.userservice.exceptions;


public class DuplicatePhoneException extends Exception {
    public DuplicatePhoneException(String message) {
        super(message);
    }
}