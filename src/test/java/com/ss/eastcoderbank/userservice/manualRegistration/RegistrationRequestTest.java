package com.ss.eastcoderbank.userservice.manualRegistration;

import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.UserRole;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class RegistrationRequestTest {
    @Test
    public void testCanEqual() {
        UserRole role = new UserRole("Dr");
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        assertFalse((new RegistrationRequest(role, "Jane", "Doe", dob, "jane.doe@example.org", "4105551212", address,
                dateJoined, new Credential())).canEqual("Other"));
    }

    @Test
    public void testCanEqual2() {
        UserRole role = new UserRole("Dr");
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(role, "Jane", "Doe", dob, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        UserRole role1 = new UserRole("Dr");
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertTrue(registrationRequest.canEqual(new RegistrationRequest(role1, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testConstructor() {
        UserRole userRole = new UserRole("Dr");
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate ofEpochDayResult1 = LocalDate.ofEpochDay(1L);
        Credential credential = new Credential();
        RegistrationRequest actualRegistrationRequest = new RegistrationRequest(userRole, "Jane", "Doe", ofEpochDayResult,
                "jane.doe@example.org", "4105551212", address, ofEpochDayResult1, credential);

        assertSame(address, actualRegistrationRequest.getAddress());
        assertSame(credential, actualRegistrationRequest.getCredential());
        LocalDate dateJoined = actualRegistrationRequest.getDateJoined();
        assertSame(ofEpochDayResult1, dateJoined);
        LocalDate dob = actualRegistrationRequest.getDob();
        assertEquals(dob, dateJoined);
        assertSame(ofEpochDayResult, dob);
        assertEquals(ofEpochDayResult1, dob);
        assertEquals("jane.doe@example.org", actualRegistrationRequest.getEmail());
        assertEquals("Jane", actualRegistrationRequest.getFirstName());
        assertEquals("Doe", actualRegistrationRequest.getLastName());
        assertEquals("4105551212", actualRegistrationRequest.getPhone());
        assertSame(userRole, actualRegistrationRequest.getRole());
        assertEquals("RegistrationRequest(role=UserRole(id=null, title=Dr, users=[]), firstName=Jane, lastName=Doe,"
                + " dob=1970-01-02, email=jane.doe@example.org, phone=4105551212, address=Address(streetAddress=null,"
                + " city=null, zip=null, state=null), dateJoined=1970-01-02, credential=Credential(username=null,"
                + " password=null))", actualRegistrationRequest.toString());
    }

    @Test
    public void testEquals() {
        UserRole role = new UserRole("Dr");
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        assertFalse((new RegistrationRequest(role, "Jane", "Doe", dob, "jane.doe@example.org", "4105551212", address,
                dateJoined, new Credential())).equals(null));
    }

    @Test
    public void testEquals10() {
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, "Jane", "Jane", dob, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals11() {
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, "Jane", null, dob, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals12() {
        LocalDate dob = LocalDate.ofEpochDay(0L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, "Jane", "Doe", dob, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals13() {
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, "Jane", "Doe", null, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, "Jane", "Doe", dob, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals14() {
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, "Jane", "Doe", dob, "Jane", "4105551212",
                address, dateJoined, new Credential());
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals15() {
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, "Jane", "Doe", dob, null, "4105551212",
                address, dateJoined, new Credential());
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals16() {
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, "Jane", "Doe", dob, "jane.doe@example.org",
                "+44 1865 4960636", address, dateJoined, new Credential());
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals17() {
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, "Jane", "Doe", dob, "jane.doe@example.org",
                null, address, dateJoined, new Credential());
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals18() {
        LocalDate dob = LocalDate.ofEpochDay(1L);
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, "Jane", "Doe", dob, "jane.doe@example.org",
                "4105551212", null, dateJoined, new Credential());
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals19() {
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, null, "Doe", dob, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, null, "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals2() {
        UserRole role = new UserRole("Dr");
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        assertFalse((new RegistrationRequest(role, "Jane", "Doe", dob, "jane.doe@example.org", "4105551212", address,
                dateJoined, new Credential())).equals("Different type to RegistrationRequest"));
    }

    @Test
    public void testEquals3() {
        UserRole role = new UserRole("Dr");
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(role, "Jane", "Doe", dob, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        assertTrue(registrationRequest.equals(registrationRequest));
        int expectedHashCodeResult = registrationRequest.hashCode();
        assertEquals(expectedHashCodeResult, registrationRequest.hashCode());
    }

    @Test
    public void testEquals4() {
        UserRole role = new UserRole("Dr");
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(role, "Jane", "Doe", dob, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        UserRole role1 = new UserRole("Dr");
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(role1, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals5() {
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, "Jane", "Doe", dob, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        UserRole role = new UserRole("Dr");
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(role, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals6() {
        UserRole role = mock(UserRole.class);
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(role, "Jane", "Doe", dob, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        UserRole role1 = new UserRole("Dr");
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(role1, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals7() {
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, "Jane", "Doe", dob, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals8() {
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, null, "Doe", dob, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }

    @Test
    public void testEquals9() {
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        RegistrationRequest registrationRequest = new RegistrationRequest(null, "Doe", "Doe", dob, "jane.doe@example.org",
                "4105551212", address, dateJoined, new Credential());
        LocalDate dob1 = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined1 = LocalDate.ofEpochDay(1L);
        assertFalse(registrationRequest.equals(new RegistrationRequest(null, "Jane", "Doe", dob1, "jane.doe@example.org",
                "4105551212", address1, dateJoined1, new Credential())));
    }
}

