package com.ss.eastcoderbank.usersapi.mapper;

import com.ss.eastcoderbank.core.model.user.User;
import com.ss.eastcoderbank.usersapi.dto.UpdateProfileDto;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateProfileMapper {

    @Mapping(source = "username", target = "credential.username")
    @Mapping(ignore = true, target = "credential.password")
    @Mapping(ignore = true, target = "role")
    void updateEntity(UpdateProfileDto updateProfileDto, @MappingTarget User user, @Context PasswordEncoder passwordEncoder);

    @AfterMapping
    default void updateEntity(@MappingTarget User user, UpdateProfileDto updateProfileDto, @Context PasswordEncoder passwordEncoder) {
        if (updateProfileDto.getPassword() != null) {
            user.getCredential().setPassword(passwordEncoder.encode(updateProfileDto.getPassword()));
        }
    }
}
