package com.ss.eastcoderbank.usersapi.service;

import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.core.repository.UserRepository;
import com.ss.eastcoderbank.core.repository.UserRoleRepository;
import com.ss.eastcoderbank.usersapi.mapper.LoginMapper;
import com.ss.eastcoderbank.usersapi.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private LoginMapper loginMapper;


    public void userLogin(LoginDto loginDto) throws Exception {
        User user = loginMapper.mapToEntity(loginDto);
    }


}
