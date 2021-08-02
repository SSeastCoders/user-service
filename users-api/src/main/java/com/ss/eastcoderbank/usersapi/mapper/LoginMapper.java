package com.ss.eastcoderbank.usersapi.mapper;

import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.usersapi.dto.LoginDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoginMapper {


    LoginMapper INSTANCE = Mappers.getMapper( LoginMapper.class );

    @Mapping(source = "username", target = "credential.username")
    @Mapping(source = "password", target = "credential.password")
    User mapToEntity(LoginDto loginDto);
}
