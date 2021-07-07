package com.ss.eastcoderbank.userservice.exceptions;

public class DuplicateEmailException extends Exception{
    public DuplicateEmailException(String message) {
        super(message);
    }
}
