package com.ss.eastcoderbank.userservice.mapper;

import com.ss.eastcoderbank.userservice.dto.UpdateProfileDto;
import com.ss.eastcoderbank.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UpdateProfileMapper {

    @Mapping(source = "username", target = "credential.username")
    @Mapping(source = "password", target = "credential.password")
    @Mapping(source = "role", target = "role.title")
    User mapToEntity(UpdateProfileDto updateProfileDto);
}
