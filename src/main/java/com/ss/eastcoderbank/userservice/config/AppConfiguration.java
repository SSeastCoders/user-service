package com.ss.eastcoderbank.userservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApplicationConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}