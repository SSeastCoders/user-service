package com.ss.eastcoderbank.usersapi.controller;

import com.ss.eastcoderbank.usersapi.service.AuthService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class AuthorizationControllerTest {
    @Test
    void testConstructor() {
        // Arrange
        AuthService authService = new AuthService();

        // Act
        AuthorizationController actualAuthorizationController = new AuthorizationController(authService);

        // Assert
        assertSame(actualAuthorizationController.authService, authService);
    }


}

