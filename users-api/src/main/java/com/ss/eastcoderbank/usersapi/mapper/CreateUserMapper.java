package com.ss.eastcoderbank.usersapi.mapper;

import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.usersapi.dto.CreateUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateUserMapper {
    @Mapping(source = "username", target = "credential.username")
    @Mapping(source = "password", target = "credential.password")
    @Mapping(source = "role", target = "role.title")
    User mapToEntity(CreateUserDto createUserDto);
}
