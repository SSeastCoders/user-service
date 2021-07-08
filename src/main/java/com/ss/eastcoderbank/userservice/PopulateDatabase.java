package com.ss.eastcoderbank.userservice;

import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import com.ss.eastcoderbank.userservice.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PopulateDatabase implements ApplicationRunner {


        private UserRepository userRepository;
        private UserRoleRepository userRoleRepository;
        private PasswordEncoder passwordEncoder;

        @Autowired
        public PopulateDatabase(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.userRoleRepository = userRoleRepository;
            this.passwordEncoder = passwordEncoder;
        }

        public void run(ApplicationArguments args) {
            UserRole userRoleAdmin = new UserRole();
            userRoleAdmin.setId(1);
            userRoleAdmin.setTitle("Administrator");

            UserRole userRoleCust = new UserRole();
            userRoleCust.setId(2);
            userRoleCust.setTitle("Customer");

            userRoleRepository.saveAll(Arrays.asList(userRoleAdmin, userRoleCust));

            User user = new User();
            user.setActiveStatus(true);
            user.setId(1);
            user.setEmail("hazel@smoothstack.com");
            user.setFirstName("hazel");
            Credential cred = new Credential();
            cred.setUsername("hazel");
            cred.setPassword(passwordEncoder.encode("hazel"));
            user.setCredential(cred);
            user.setRole(userRoleAdmin);

            userRepository.save(user);

            //(1, TRUE, 'Boston', 'MA', '41 Bothwell Road', 02135, 'tempPass', 'hazel', '2021-07-07', '1996-06-28', 'hazel.baker-harvey@smoothstack.com', 'Hazel', 'Baker-Harvey', '(206) 557-0334', 1);


        }
}
