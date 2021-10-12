package com.ss.eastcoderbank.usersapi.security;

import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserPrincipalService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserPrincipalService.class);

    private UserRepository userRepository;

    public UserPrincipalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LOGGER.trace("UserPrincipalService.loadByUsername reached...");
        User user = this.userRepository.findByCredentialUsername(userName);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }
}
