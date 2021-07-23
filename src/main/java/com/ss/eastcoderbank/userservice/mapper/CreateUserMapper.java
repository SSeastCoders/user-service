package com.ss.eastcoderbank.userservice.mapper;

import com.ss.eastcoderbank.userservice.dto.CreateUserDto;
import com.ss.eastcoderbank.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateUserMapper {
    @Mapping(source = "username", target = "credential.username")
    @Mapping(source = "password", target = "credential.password")
    @Mapping(source = "role", target = "role.title")
    User mapToEntity(CreateUserDto createUserDto);
}
