package com.ss.eastcoderbank.userservice.mapper;

import com.ss.eastcoderbank.userservice.dto.UserDto;
import com.ss.eastcoderbank.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "credential.username", target = "username")
    UserDto mapToDto(User user);
}
