package com.ss.eastcoderbank.usersapi.controller;

import com.ss.eastcoderbank.usersapi.service.AuthorizationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class AuthorizationControllerTest {
    @Test
    public void testConstructor() {
        // Arrange
        AuthorizationService authorizationService = new AuthorizationService();

        // Act
        AuthorizationController actualAuthorizationController = new AuthorizationController(authorizationService);

        // Assert
        assertSame(actualAuthorizationController.authorizationService, authorizationService);
    }


}

