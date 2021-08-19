package com.ss.eastcoderbank.usersapi.validation.validators;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.junit.jupiter.api.Test;

import javax.validation.ClockProvider;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class PhoneNumberValidatorTest {
    @Test
    public void testIsValid() {
        // Arrange
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        String s = "(";
        ClockProvider clockProvider = mock(ClockProvider.class);
        ConstraintValidatorContextImpl context = new ConstraintValidatorContextImpl(clockProvider,
                PathImpl.createRootPath(), null, "Constraint Validator Payload", ExpressionLanguageFeatureLevel.DEFAULT,
                ExpressionLanguageFeatureLevel.DEFAULT);

        // Act
        boolean actualIsValidResult = phoneNumberValidator.isValid(s, context);

        // Assert
        assertFalse(actualIsValidResult);
    }

    @Test
    public void testIsValid2() {
        // Arrange
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        String s = null;
        ClockProvider clockProvider = mock(ClockProvider.class);
        ConstraintValidatorContextImpl context = new ConstraintValidatorContextImpl(clockProvider,
                PathImpl.createRootPath(), null, "Constraint Validator Payload", ExpressionLanguageFeatureLevel.DEFAULT,
                ExpressionLanguageFeatureLevel.DEFAULT);

        // Act
        boolean actualIsValidResult = phoneNumberValidator.isValid(s, context);

        // Assert
        assertTrue(actualIsValidResult);
    }

    @Test
    public void testIsValid3() {
        // Arrange
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        String s = "";
        ClockProvider clockProvider = mock(ClockProvider.class);
        ConstraintValidatorContextImpl context = new ConstraintValidatorContextImpl(clockProvider,
                PathImpl.createRootPath(), null, "Constraint Validator Payload", ExpressionLanguageFeatureLevel.DEFAULT,
                ExpressionLanguageFeatureLevel.DEFAULT);

        // Act
        boolean actualIsValidResult = phoneNumberValidator.isValid(s, context);

        // Assert
        assertFalse(actualIsValidResult);
    }
}

