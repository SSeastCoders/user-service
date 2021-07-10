package com.ss.eastcoderbank.userservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
public class PasswordEncoderTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testBCryptPasswordEncoder() {

        this.passwordEncoder.bCryptPasswordEncoder();
    }

    @Test
    public void testBCryptPasswordEncoder2() {

        this.passwordEncoder.bCryptPasswordEncoder();
    }
}

