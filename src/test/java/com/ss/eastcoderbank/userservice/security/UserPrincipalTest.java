package com.ss.eastcoderbank.userservice.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

public class UserPrincipalTest {
    @Test
    public void testConstructor() {
        UserPrincipal actualUserPrincipal = new UserPrincipal(new User());
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");
        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");
        User user = new User();
        user.setLastName("Doe");
        user.setCredential(credential);
        user.setEmail("jane.doe@example.org");
        user.setAddress(address);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setPhone("4105551212");
        user.setFirstName("Jane");
        user.setDataJoined(LocalDate.ofEpochDay(1L));
        user.setActiveStatus(true);
        user.setRole(userRole);
        actualUserPrincipal.setUser(user);
        assertSame(user, actualUserPrincipal.getUser());
    }

    @Test
    public void testGetAuthorities() {
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        User user = new User();
        user.setRole(userRole);
        Collection<? extends GrantedAuthority> actualAuthorities = (new UserPrincipal(user)).getAuthorities();
        assertEquals(1, actualAuthorities.size());
        assertEquals("Dr", ((List<? extends GrantedAuthority>) actualAuthorities).get(0).getAuthority());
    }

    @Test
    public void testGetAuthorities2() {
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        User user = new User();
        user.setLastName("Doe");
        user.setCredential(credential);
        user.setEmail("jane.doe@example.org");
        user.setAddress(address);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setPhone("4105551212");
        user.setFirstName("Jane");
        user.setDataJoined(LocalDate.ofEpochDay(1L));
        user.setActiveStatus(true);
        user.setRole(userRole);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);
        Collection<? extends GrantedAuthority> actualAuthorities = userPrincipal.getAuthorities();
        assertEquals(1, actualAuthorities.size());
        assertEquals("Dr", ((List<? extends GrantedAuthority>) actualAuthorities).get(0).getAuthority());
    }

    @Test
    public void testGetPassword() {
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        User user = new User();
        user.setCredential(credential);
        assertEquals("iloveyou", (new UserPrincipal(user)).getPassword());
    }

    @Test
    public void testGetPassword2() {
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        User user = new User();
        user.setLastName("Doe");
        user.setCredential(credential);
        user.setEmail("jane.doe@example.org");
        user.setAddress(address);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setPhone("4105551212");
        user.setFirstName("Jane");
        user.setDataJoined(LocalDate.ofEpochDay(1L));
        user.setActiveStatus(true);
        user.setRole(userRole);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);
        assertEquals("iloveyou", userPrincipal.getPassword());
    }

    @Test
    public void testGetUsername() {
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        User user = new User();
        user.setCredential(credential);
        assertEquals("janedoe", (new UserPrincipal(user)).getUsername());
    }

    @Test
    public void testGetUsername2() {
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        User user = new User();
        user.setLastName("Doe");
        user.setCredential(credential);
        user.setEmail("jane.doe@example.org");
        user.setAddress(address);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setPhone("4105551212");
        user.setFirstName("Jane");
        user.setDataJoined(LocalDate.ofEpochDay(1L));
        user.setActiveStatus(true);
        user.setRole(userRole);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);
        assertEquals("janedoe", userPrincipal.getUsername());
    }

    @Test
    public void testIsAccountNonExpired() {
        assertFalse((new UserPrincipal(new User())).isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonExpired2() {
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        User user = new User();
        user.setLastName("Doe");
        user.setCredential(credential);
        user.setEmail("jane.doe@example.org");
        user.setAddress(address);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setPhone("4105551212");
        user.setFirstName("Jane");
        user.setDataJoined(LocalDate.ofEpochDay(1L));
        user.setActiveStatus(true);
        user.setRole(userRole);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);
        assertTrue(userPrincipal.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertFalse((new UserPrincipal(new User())).isAccountNonLocked());
    }

    @Test
    public void testIsAccountNonLocked2() {
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        User user = new User();
        user.setLastName("Doe");
        user.setCredential(credential);
        user.setEmail("jane.doe@example.org");
        user.setAddress(address);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setPhone("4105551212");
        user.setFirstName("Jane");
        user.setDataJoined(LocalDate.ofEpochDay(1L));
        user.setActiveStatus(true);
        user.setRole(userRole);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);
        assertTrue(userPrincipal.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertFalse((new UserPrincipal(new User())).isCredentialsNonExpired());
    }

    @Test
    public void testIsCredentialsNonExpired2() {
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        User user = new User();
        user.setLastName("Doe");
        user.setCredential(credential);
        user.setEmail("jane.doe@example.org");
        user.setAddress(address);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setPhone("4105551212");
        user.setFirstName("Jane");
        user.setDataJoined(LocalDate.ofEpochDay(1L));
        user.setActiveStatus(true);
        user.setRole(userRole);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);
        assertTrue(userPrincipal.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertFalse((new UserPrincipal(new User())).isEnabled());
    }

    @Test
    public void testIsEnabled2() {
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        User user = new User();
        user.setLastName("Doe");
        user.setCredential(credential);
        user.setEmail("jane.doe@example.org");
        user.setAddress(address);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setPhone("4105551212");
        user.setFirstName("Jane");
        user.setDataJoined(LocalDate.ofEpochDay(1L));
        user.setActiveStatus(true);
        user.setRole(userRole);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);
        assertTrue(userPrincipal.isEnabled());
    }
}

