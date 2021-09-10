package com.ss.eastcoderbank.usersapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Map;

@SpringBootApplication(scanBasePackages = "com.ss.eastcoderbank.*")
@EntityScan(basePackages = "com.ss.eastcoderbank.core.model")
@EnableJpaRepositories(basePackages = "com.ss.eastcoderbank.core.repository")
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
