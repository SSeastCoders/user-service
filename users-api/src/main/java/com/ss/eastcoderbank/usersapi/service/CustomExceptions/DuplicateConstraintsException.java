package com.ss.eastcoderbank.usersapi.service.CustomExceptions;


public class DuplicateConstraintsException  extends RuntimeException {

    public DuplicateConstraintsException(String message) {
        super(message);
    }
}
