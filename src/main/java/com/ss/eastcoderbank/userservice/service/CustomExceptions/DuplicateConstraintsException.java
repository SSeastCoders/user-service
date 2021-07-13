package com.ss.eastcoderbank.userservice.service.CustomExceptions;

public class DuplicateConstraintsException  extends RuntimeException {
    public DuplicateConstraintsException(String message) {
        super(message);
    }
}
