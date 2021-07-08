package com.ss.eastcoderbank.userservice.manualRegistration;

import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ModelMapper.class, EmailValidator.class, RegistrationService.class, UserService.class})
@ExtendWith(SpringExtension.class)
public class RegistrationServiceTest {
    @MockBean
    private EmailValidator emailValidator;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private RegistrationService registrationService;

    @MockBean
    private UserService userService;


    @Test
    public void testManualRegister() throws Exception {
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenThrow(new IllegalStateException("foo"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        when(this.emailValidator.test(anyString())).thenReturn(true);
        UserRole role = new UserRole("Dr");
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        assertThrows(IllegalStateException.class,
                () -> this.registrationService.manualRegister(new RegistrationRequest(role, "Jane", "Doe", dob,
                        "jane.doe@example.org", "4105551212", address, dateJoined, new Credential())));
        verify(this.modelMapper).getConfiguration();
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
        verify(this.emailValidator).test(anyString());
    }

    @Test
    public void testManualRegister2() throws Exception {
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
        when(this.userService.manuallyCreateUser((User) any())).thenReturn(user);

        Credential credential1 = new Credential();
        credential1.setPassword("iloveyou");
        credential1.setUsername("janedoe");

        Address address1 = new Address();
        address1.setZip(0);
        address1.setCity("Oxford");
        address1.setStreetAddress("42 Main St");
        address1.setState("MD");

        UserRole userRole1 = new UserRole();
        userRole1.setUsers(new HashSet<User>());
        userRole1.setId(1);
        userRole1.setTitle("Dr");

        User user1 = new User();
        user1.setLastName("Doe");
        user1.setCredential(credential1);
        user1.setEmail("jane.doe@example.org");
        user1.setAddress(address1);
        user1.setDob(LocalDate.ofEpochDay(1L));
        user1.setId(1);
        user1.setPhone("4105551212");
        user1.setFirstName("Jane");
        user1.setDataJoined(LocalDate.ofEpochDay(1L));
        user1.setActiveStatus(true);
        user1.setRole(userRole1);
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(user1);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        when(this.emailValidator.test(anyString())).thenReturn(true);
        UserRole role = new UserRole("Dr");
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address2 = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        assertSame(user, this.registrationService.manualRegister(new RegistrationRequest(role, "Jane", "Doe", dob,
                "jane.doe@example.org", "4105551212", address2, dateJoined, new Credential())));
        verify(this.userService).manuallyCreateUser((User) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
        verify(this.emailValidator).test(anyString());
    }

    @Test
    public void testManualRegister3() throws Exception {
        when(this.userService.manuallyCreateUser((User) any())).thenThrow(new IllegalStateException("42"));

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
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(user);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        when(this.emailValidator.test(anyString())).thenReturn(true);
        UserRole role = new UserRole("Dr");
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        assertThrows(IllegalStateException.class,
                () -> this.registrationService.manualRegister(new RegistrationRequest(role, "Jane", "Doe", dob,
                        "jane.doe@example.org", "4105551212", address1, dateJoined, new Credential())));
        verify(this.userService).manuallyCreateUser((User) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
        verify(this.emailValidator).test(anyString());
    }

    @Test
    public void testManualRegister4() throws Exception {
        when(this.userService.manuallyCreateUser((User) any())).thenThrow(new IllegalStateException("42"));

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
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(user);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        when(this.emailValidator.test(anyString())).thenReturn(false);
        UserRole role = new UserRole("Dr");
        LocalDate dob = LocalDate.ofEpochDay(1L);
        Address address1 = new Address();
        LocalDate dateJoined = LocalDate.ofEpochDay(1L);
        assertThrows(IllegalStateException.class,
                () -> this.registrationService.manualRegister(new RegistrationRequest(role, "Jane", "Doe", dob,
                        "jane.doe@example.org", "4105551212", address1, dateJoined, new Credential())));
        verify(this.emailValidator).test(anyString());
    }
}

