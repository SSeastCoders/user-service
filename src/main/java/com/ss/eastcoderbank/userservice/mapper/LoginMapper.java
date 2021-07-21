package com.ss.eastcoderbank.userservice.mapper;

import com.ss.eastcoderbank.userservice.dto.LoginDto;
import com.ss.eastcoderbank.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface LoginMapper {


    LoginMapper INSTANCE = Mappers.getMapper( LoginMapper.class );

    @Mapping(source = "username", target = "credential.username")
    @Mapping(source = "password", target = "credential.password")
    User mapToEntity(LoginDto loginDto);
}
