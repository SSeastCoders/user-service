package com.ss.eastcoderbank.userservice;

import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import com.ss.eastcoderbank.userservice.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableConfigurationProperties
public class UserServiceApplication {

//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserRoleRepository userRoleRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {

        SpringApplication.run(UserServiceApplication.class, args);
    }

    public void environmentSetUp(ProcessBuilder processBuilder) {
        Map<String, String> env = processBuilder.environment();
        env.put("JWT_SECRET", "verysupersecetcode123");
    }

    @Bean
    CommandLineRunner run(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        UserRole userRoleAdmin = new UserRole();
        userRoleAdmin.setId(1);
        userRoleAdmin.setTitle("Admin");
        UserRole userRoleCust = new UserRole();
        userRoleCust.setId(2);
        userRoleCust.setTitle("Customer");

        userRoleRepository.saveAll(Arrays.asList(userRoleAdmin, userRoleCust));

        return args -> IntStream.rangeClosed(1, 30).forEach(i -> {
            User user = new User();
            user.setActiveStatus(true);
            user.setId(i);
            user.setEmail("user" + i + "@smoothstack.com");
            user.setFirstName("firstName" + i);
            user.setLastName("lastName" + i);
            Credential cred = new Credential();
            cred.setUsername("user" + i);
            cred.setPassword(passwordEncoder.encode("hazel"));
            user.setCredential(cred);
            user.setRole(userRoleAdmin);
            userRepository.save(user);
        });


//        return args -> IntStream.rangeClosed(1, 30).forEach(i -> {
//            User userCust = new User();
//            userCust.setActiveStatus(true);
//            userCust.setId(2);
//            userCust.setEmail("customer" + i + "@smoothstack.com");
//            userCust.setFirstName("customer" + i);
//            Credential credCust = new Credential();
//            credCust.setUsername("customer" + i);
//            credCust.setPassword(passwordEncoder.encode("customer"));
//            userCust.setCredential(credCust);
//            userCust.setRole(userRoleCust);
//
//            userRepository.save(userCust);
//        });

        //return null;
    }



    }
