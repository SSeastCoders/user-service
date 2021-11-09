package com.ss.eastcoderbank.usersapi.service;

import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.core.repository.UserRoleRepository;
import com.ss.eastcoderbank.usersapi.dto.LoginDto;
import com.ss.eastcoderbank.usersapi.mapper.LoginMapper;
import com.ss.eastcoderbank.usersapi.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private LoginMapper loginMapper;


    public void userLogin(LoginDto loginDto) throws RuntimeException {
        loginMapper.mapToEntity(loginDto);
    }

    public UserPrincipal findUser(String s) throws UsernameNotFoundException {
        User user = userRepository.findByCredentialUsername(s);

        if (user == null) throw new UsernameNotFoundException(s);

        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(user.getRole().getTitle());
        authorities.add(role);

        return new UserPrincipal(user);
    }


}
