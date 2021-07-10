package com.ss.eastcoderbank.userservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ApplicationConfiguration.class})
@ExtendWith(SpringExtension.class)
public class ApplicationConfigurationTest {
    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    @Test
    public void testModelMapper() {

        this.applicationConfiguration.modelMapper();
    }
}

