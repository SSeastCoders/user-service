package com.ss.eastcoderbank.usersapi.security;

import com.ss.eastcoderbank.core.model.user.Address;
import com.ss.eastcoderbank.core.model.user.Credential;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.model.user.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserPrincipalTest {
    @Test
    public void testConstructor() {
        // Arrange
        User user = new User();

        // Act
        UserPrincipal actualUserPrincipal = new UserPrincipal(user);
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");
        Address address = new Address();
        address.setZip(1);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");
        User user1 = new User();
        user1.setLastName("Doe");
        user1.setEmail("jane.doe@example.org");
        user1.setRole(userRole);
        user1.setDob(LocalDate.ofEpochDay(1L));
        user1.setId(1);
        user1.setActiveStatus(true);
        user1.setPhone("4105551212");
        user1.setCredential(credential);
        user1.setFirstName("Jane");
        user1.setDateJoined(LocalDate.ofEpochDay(1L));
        user1.setAddress(address);
        actualUserPrincipal.setUser(user1);

        // Assert
        assertSame(user1, actualUserPrincipal.getUser());
    }

    @Test
    public void testGetAuthorities() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        User user = new User();
        user.setRole(userRole);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        Collection<? extends GrantedAuthority> actualAuthorities = userPrincipal.getAuthorities();

        // Assert
        assertEquals(1, actualAuthorities.size());
        assertEquals("Dr", ((List<? extends GrantedAuthority>) actualAuthorities).get(0).getAuthority());
    }

    @Test
    public void testGetAuthorities2() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);

        // Act
        Collection<? extends GrantedAuthority> actualAuthorities = userPrincipal.getAuthorities();

        // Assert
        assertEquals(1, actualAuthorities.size());
        assertEquals("Dr", ((List<? extends GrantedAuthority>) actualAuthorities).get(0).getAuthority());
    }

    @Test
    public void testGetPassword() {
        // Arrange
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        User user = new User();
        user.setCredential(credential);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        String actualPassword = userPrincipal.getPassword();

        // Assert
        assertEquals("iloveyou", actualPassword);
    }

    @Test
    public void testGetPassword2() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);

        // Act
        String actualPassword = userPrincipal.getPassword();

        // Assert
        assertEquals("iloveyou", actualPassword);
    }

    @Test
    public void testGetUsername() {
        // Arrange
        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        User user = new User();
        user.setCredential(credential);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        String actualUsername = userPrincipal.getUsername();

        // Assert
        assertEquals("janedoe", actualUsername);
    }

    @Test
    public void testGetUsername2() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);

        // Act
        String actualUsername = userPrincipal.getUsername();

        // Assert
        assertEquals("janedoe", actualUsername);
    }

    @Test
    public void testIsAccountNonExpired() {
        // Arrange
        User user = new User();
        user.setActiveStatus(true);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        boolean actualIsAccountNonExpiredResult = userPrincipal.isAccountNonExpired();

        // Assert
        assertTrue(actualIsAccountNonExpiredResult);
    }

    @Test
    public void testIsAccountNonExpired2() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);

        // Act
        boolean actualIsAccountNonExpiredResult = userPrincipal.isAccountNonExpired();

        // Assert
        assertTrue(actualIsAccountNonExpiredResult);
    }

    @Test
    public void testIsAccountNonExpired3() {
        // Arrange
        User user = new User();
        user.setActiveStatus(false);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        boolean actualIsAccountNonExpiredResult = userPrincipal.isAccountNonExpired();

        // Assert
        assertFalse(actualIsAccountNonExpiredResult);
    }

    @Test
    public void testIsAccountNonLocked() {
        // Arrange
        User user = new User();
        user.setActiveStatus(true);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        boolean actualIsAccountNonLockedResult = userPrincipal.isAccountNonLocked();

        // Assert
        assertTrue(actualIsAccountNonLockedResult);
    }

    @Test
    public void testIsAccountNonLocked2() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);

        // Act
        boolean actualIsAccountNonLockedResult = userPrincipal.isAccountNonLocked();

        // Assert
        assertTrue(actualIsAccountNonLockedResult);
    }

    @Test
    public void testIsAccountNonLocked3() {
        // Arrange
        User user = new User();
        user.setActiveStatus(false);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        boolean actualIsAccountNonLockedResult = userPrincipal.isAccountNonLocked();

        // Assert
        assertFalse(actualIsAccountNonLockedResult);
    }

    @Test
    public void testIsCredentialsNonExpired() {
        // Arrange
        User user = new User();
        user.setActiveStatus(true);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        boolean actualIsCredentialsNonExpiredResult = userPrincipal.isCredentialsNonExpired();

        // Assert
        assertTrue(actualIsCredentialsNonExpiredResult);
    }

    @Test
    public void testIsCredentialsNonExpired2() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);

        // Act
        boolean actualIsCredentialsNonExpiredResult = userPrincipal.isCredentialsNonExpired();

        // Assert
        assertTrue(actualIsCredentialsNonExpiredResult);
    }

    @Test
    public void testIsCredentialsNonExpired3() {
        // Arrange
        User user = new User();
        user.setActiveStatus(false);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        boolean actualIsCredentialsNonExpiredResult = userPrincipal.isCredentialsNonExpired();

        // Assert
        assertFalse(actualIsCredentialsNonExpiredResult);
    }

    @Test
    public void testIsEnabled() {
        // Arrange
        User user = new User();
        user.setActiveStatus(true);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        boolean actualIsEnabledResult = userPrincipal.isEnabled();

        // Assert
        assertTrue(actualIsEnabledResult);
    }

    @Test
    public void testIsEnabled2() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setUsers(new HashSet<User>());
        userRole.setId(1);
        userRole.setTitle("Dr");

        Credential credential = new Credential();
        credential.setPassword("iloveyou");
        credential.setUsername("janedoe");

        Address address = new Address();
        address.setZip(0);
        address.setCity("Oxford");
        address.setStreetAddress("42 Main St");
        address.setState("MD");

        User user = new User();
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.org");
        user.setRole(userRole);
        user.setDob(LocalDate.ofEpochDay(1L));
        user.setId(1);
        user.setActiveStatus(true);
        user.setPhone("4105551212");
        user.setCredential(credential);
        user.setFirstName("Jane");
        user.setDateJoined(LocalDate.ofEpochDay(1L));
        user.setAddress(address);

        UserPrincipal userPrincipal = new UserPrincipal(null);
        userPrincipal.setUser(user);

        // Act
        boolean actualIsEnabledResult = userPrincipal.isEnabled();

        // Assert
        assertTrue(actualIsEnabledResult);
    }

    @Test
    public void testIsEnabled3() {
        // Arrange
        User user = new User();
        user.setActiveStatus(false);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        boolean actualIsEnabledResult = userPrincipal.isEnabled();

        // Assert
        assertFalse(actualIsEnabledResult);
    }
}

