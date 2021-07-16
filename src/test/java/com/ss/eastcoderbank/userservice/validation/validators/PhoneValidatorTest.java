package com.ss.eastcoderbank.userservice.validation.validators;

import com.ss.eastcoderbank.userservice.dto.CreateUserDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PhoneValidatorTest {
    @Autowired
    Validator validator;

    @Test
    public void validatorTest() {
        String invalidPhone = "23434445g3gfdg";
        String validPhone = "770-617-5446";
        String invalidPhone2 = "274-832-544i";

        CreateUserDto dto = new CreateUserDto("happy", "password", "good@gmail.com", validPhone);
        Set<ConstraintViolation<CreateUserDto>> violations;
        violations = validator.validate(dto);
        assertEquals(0, violations.size());

        dto.setPhone(invalidPhone);
        violations = validator.validate(dto);
        assertEquals(1, violations.size());

        dto.setPhone(invalidPhone2);
        violations = validator.validate(dto);
        assertEquals(1, violations.size());


    }
}
