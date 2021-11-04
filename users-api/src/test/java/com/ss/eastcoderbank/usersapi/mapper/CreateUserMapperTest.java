package com.ss.eastcoderbank.usersapi.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserMapperTest {

    @Test
    void shouldInit() {
        CreateUserMapper fakeInstance = Mappers.getMapper( CreateUserMapper.class );
        assertTrue(fakeInstance instanceof CreateUserMapper );

    }

}