package com.ss.eastcoderbank.usersapi.model;

import com.ss.eastcoderbank.core.model.user.Credential;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CredentialTest {
    @Test
    public void testConstructor() {
        Credential actualCredential = new Credential();
        actualCredential.setPassword("iloveyou");
        actualCredential.setUsername("janedoe");
        assertEquals("iloveyou", actualCredential.getPassword());
        assertEquals("janedoe", actualCredential.getUsername());
        assertEquals("Credential(username=janedoe, password=iloveyou)", actualCredential.toString());
    }
}

