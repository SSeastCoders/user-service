package com.ss.eastcoderbank.usersapi.service.customexceptions;


public class DuplicateConstraintsException  extends RuntimeException {

    public DuplicateConstraintsException(String message) {
        super(message);
    }
}
