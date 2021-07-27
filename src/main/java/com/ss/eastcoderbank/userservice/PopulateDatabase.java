//package com.ss.eastcoderbank.userservice;
//
//import com.ss.eastcoderbank.userservice.model.Credential;
//import com.ss.eastcoderbank.userservice.model.User;
//import com.ss.eastcoderbank.userservice.model.UserRole;
//import com.ss.eastcoderbank.userservice.repository.UserRepository;
//import com.ss.eastcoderbank.userservice.repository.UserRoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.stream.IntStream;
//
//@Component
//public class PopulateDatabase implements ApplicationRunner {
//
//
//    private UserRepository userRepository;
//    private UserRoleRepository userRoleRepository;
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public PopulateDatabase(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.userRoleRepository = userRoleRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public void run(ApplicationArguments args) {
//        UserRole userRoleAdmin = new UserRole();
//        userRoleAdmin.setId(1);
//        userRoleAdmin.setTitle("Admin");
//        UserRole userRoleCust = new UserRole();
//        userRoleCust.setId(2);
//        userRoleCust.setTitle("Customer");
//
//        userRoleRepository.saveAll(Arrays.asList(userRoleAdmin, userRoleCust));
//        IntStream.rangeClosed(1, 30).forEach(i -> {
//                    User user = new User();
//                    user.setActiveStatus(true);
//                    user.setId(1);
//                    user.setEmail("admin"+ i +"@smoothstack.com");
//                    user.setFirstName("admin" + i);
//                    Credential cred = new Credential();
//                    cred.setUsername("admin" + i);
//                    cred.setPassword(passwordEncoder.encode("hazel"));
//                    user.setCredential(cred);
//                    user.setRole(userRoleAdmin);
//                    userRepository.save(user);
//                });
//
//
//        IntStream.rangeClosed(1, 30).forEach(i -> {
//                    User userCust = new User();
//                    userCust.setActiveStatus(true);
//                    userCust.setId(2);
//                    userCust.setEmail("customer" + i + "@smoothstack.com");
//                    userCust.setFirstName("customer" + i);
//                    Credential credCust = new Credential();
//                    credCust.setUsername("customer" + i);
//                    credCust.setPassword(passwordEncoder.encode("customer"));
//                    userCust.setCredential(credCust);
//                    userCust.setRole(userRoleCust);
//
//                    userRepository.save(userCust);
//                });
//
//        //(1, TRUE, 'Boston', 'MA', '41 Bothwell Road', 02135, 'tempPass', 'hazel', '2021-07-07', '1996-06-28', 'hazel.baker-harvey@smoothstack.com', 'Hazel', 'Baker-Harvey', '(206) 557-0334', 1);
//
//
//    }
//}
