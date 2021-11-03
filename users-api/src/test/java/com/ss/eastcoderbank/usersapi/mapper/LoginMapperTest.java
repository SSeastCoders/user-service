package com.ss.eastcoderbank.usersapi.mapper;

import com.ss.eastcoderbank.usersapi.dto.LoginDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.User;

import static org.junit.jupiter.api.Assertions.*;

class LoginMapperTest {

    private LoginDto mockloginDto;


    @Test
    public void shouldInit() {
        LoginMapper fakeInstance = Mappers.getMapper( LoginMapper.class );
        assertTrue(fakeInstance instanceof LoginMapper );

    }
}