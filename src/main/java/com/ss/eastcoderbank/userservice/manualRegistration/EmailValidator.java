package com.ss.eastcoderbank.userservice.manualRegistration;


import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String email) {
                Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        	if(!matcher.find()) {
                return false;
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                        "Invalid email format. Please try again.");
            }
        	else {
                return true;
            }

    }





}
