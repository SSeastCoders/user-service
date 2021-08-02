package com.ss.eastcoderbank.usersapi.security;

//@ContextConfiguration(classes = {UserPrincipalService.class})
//@ExtendWith(SpringExtension.class)
//public class UserPrincipalServiceTest {
//    @Autowired
//    private UserPrincipalService userPrincipalService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @Test
//    public void testLoadUserByUsername() throws UsernameNotFoundException {
//        Credential credential = new Credential();
//        credential.setPassword("iloveyou");
//        credential.setUsername("janedoe");
//
//        Address address = new Address();
//        address.setZip(1);
//        address.setCity("Oxford");
//        address.setStreetAddress("42 Main St");
//        address.setState("MD");
//
//        UserRole userRole = new UserRole();
//        userRole.setUsers(new HashSet<User>());
//        userRole.setId(1);
//        userRole.setTitle("Dr");
//
//        User user = new User();
//        user.setLastName("Doe");
//        user.setCredential(credential);
//        user.setEmail("jane.doe@example.org");
//        user.setAddress(address);
//        user.setDob(LocalDate.ofEpochDay(1L));
//        user.setId(1);
//        user.setPhone("4105551212");
//        user.setFirstName("Jane");
//        user.setDateJoined(LocalDate.ofEpochDay(1L));
//        user.setActiveStatus(true);
//        user.setRole(userRole);
//        when(this.userRepository.findByCredentialUsername(anyString())).thenReturn(user);
//        assertTrue(this.userPrincipalService.loadUserByUsername("janedoe").isEnabled());
//        verify(this.userRepository).findByCredentialUsername(anyString());
//    }
//}

