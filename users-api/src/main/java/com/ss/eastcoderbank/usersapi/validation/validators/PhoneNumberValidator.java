package com.ss.eastcoderbank.usersapi.validation.validators;

import com.ss.eastcoderbank.usersapi.validation.annotation.ValidPhoneNumber;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.lookups.v1.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Value("${twilio.sid}")
    private String twillioAccountSid;
    @Value("${twilio.token}")
    private String twillioAuthToken;

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        Twilio.init(twillioAccountSid, twillioAuthToken);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if (s == null) return true;
        s = s.replaceAll("[\\\\s()-]", "");
        if ("".equals(s)) return false;
        try {
            PhoneNumber.fetcher(new com.twilio.type.PhoneNumber(s)).fetch();
            return true;
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) return false;
            throw e;
        }
    }
}
