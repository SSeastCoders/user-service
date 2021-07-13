package com.ss.eastcoderbank.userservice.service.CustomExceptions;

public class DuplicateConstraintsException  extends Exception{
    public DuplicateConstraintsException(String message) {
        super(message);
    }
}
