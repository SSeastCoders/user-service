package com.ss.eastcoderbank.usersapi.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UpdateProfileMapperTest {
    @Test
    void shouldInit() {
        UpdateProfileMapper fakeInstance = Mappers.getMapper( UpdateProfileMapper.class );
        assertTrue(fakeInstance instanceof UpdateProfileMapper );

    }
}