package com.ss.eastcoderbank.usersapi;

import com.ss.eastcoderbank.core.model.user.Credential;
import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.model.user.UserRole;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.core.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.IntStream;

@Profile("h2")
@Component
public class PopulateDatabase implements ApplicationRunner {


    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;

    private String adminUsername = "hazel";
    private String joinDate = "2021-06-01";
    private String fakeCustomer = "customer";
    @Autowired
    public PopulateDatabase(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(ApplicationArguments args) {
        UserRole userRoleAdmin = new UserRole();
        userRoleAdmin.setId(1);
        userRoleAdmin.setTitle("Admin");

        UserRole userRoleCust = new UserRole();
        userRoleCust.setId(2);
        userRoleCust.setTitle("Customer");

        userRoleRepository.saveAll(Arrays.asList(userRoleAdmin, userRoleCust));

        User user = new User();
        user.setActiveStatus(true);
        user.setId(1);
        user.setEmail("hazel@smoothstack.com");
        user.setFirstName(adminUsername);
        user.setLastName("hazelsLastName");
        Credential cred = new Credential();
        cred.setUsername(adminUsername);
        cred.setPassword(passwordEncoder.encode("hazel"));
        user.setCredential(cred);
        user.setRole(userRoleAdmin);
        user.setDateJoined(LocalDate.parse(joinDate));

        userRepository.save(user);

        User userCust = new User();
        userCust.setActiveStatus(true);
        userCust.setId(2);
        userCust.setEmail("customer@smoothstack.com");
        userCust.setFirstName(fakeCustomer);
        userCust.setLastName("customersLastName");
        Credential credCust = new Credential();
        credCust.setUsername(fakeCustomer);
        credCust.setPassword(passwordEncoder.encode("customer"));
        userCust.setCredential(credCust);
        userCust.setRole(userRoleCust);
        userCust.setDateJoined(LocalDate.parse(joinDate));

        userRepository.save(userCust);

        IntStream.rangeClosed(1, 30).forEach(i -> {
                    User user2 = new User();
                    user2.setActiveStatus(true);
                    user2.setId(i+2);
                    user2.setEmail("user" + i + "@smoothstack.com");
                    user2.setFirstName("firstName" + i);
                    user2.setLastName("lastName" + i);
                    Credential cred2 = new Credential();
                    cred2.setUsername("user" + i);
                    cred2.setPassword(passwordEncoder.encode("hazelasd"));
                    user2.setCredential(cred2);
                    user2.setRole(userRoleAdmin);
                    user2.setDateJoined(LocalDate.parse(joinDate));
                    userRepository.save(user2);
                });

    }
}
