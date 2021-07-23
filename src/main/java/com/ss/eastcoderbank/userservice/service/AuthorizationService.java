package com.ss.eastcoderbank.userservice.service;

import com.ss.eastcoderbank.userservice.dto.LoginDto;
import com.ss.eastcoderbank.userservice.mapper.LoginMapper;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.repository.UserRepository;
import com.ss.eastcoderbank.userservice.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
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
