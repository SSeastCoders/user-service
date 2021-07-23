package com.ss.eastcoderbank.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    public void environmentSetUp(ProcessBuilder processBuilder) {
        Map<String, String> env = processBuilder.environment();
        env.put("JWT_SECRET", "verysupersecetcode123");
    }


}
