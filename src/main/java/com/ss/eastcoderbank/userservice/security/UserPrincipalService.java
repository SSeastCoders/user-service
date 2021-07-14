package com.ss.eastcoderbank.userservice.security;

import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalService implements UserDetailsService {

    private UserRepository userRepository;

    public UserPrincipalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = this.userRepository.findByCredentialUsername(userName);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }
}
