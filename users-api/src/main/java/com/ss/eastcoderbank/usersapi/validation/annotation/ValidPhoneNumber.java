package com.ss.eastcoderbank.usersapi.validation.annotation;



import com.ss.eastcoderbank.usersapi.validation.validators.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface ValidPhoneNumber {
    String message() default "Please enter valid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
