package com.ss.eastcoderbank.usersapi.model;

import com.ss.eastcoderbank.core.model.user.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {
    @Test
    public void testConstructor() {
        Address actualAddress = new Address();
        actualAddress.setCity("Oxford");
        actualAddress.setState("MD");
        actualAddress.setStreetAddress("42 Main St");
        actualAddress.setZip(1);
        assertEquals("Oxford", actualAddress.getCity());
        assertEquals("MD", actualAddress.getState());
        assertEquals("42 Main St", actualAddress.getStreetAddress());
        assertEquals(1, actualAddress.getZip().intValue());
        assertEquals("Address(streetAddress=42 Main St, city=Oxford, zip=1, state=MD)", actualAddress.toString());
    }
}

