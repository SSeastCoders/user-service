package com.ss.eastcoderbank.usersapi.mapper;

import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.usersapi.dto.UpdateProfileDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateProfileMapper {

    @Mapping(source = "username", target = "credential.username")
    @Mapping(source = "password", target = "credential.password")
    @Mapping(ignore = true, target = "role")
    void updateEntity(UpdateProfileDto updateProfileDto, @MappingTarget User user);
}
