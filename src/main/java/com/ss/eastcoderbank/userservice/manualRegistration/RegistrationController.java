package com.ss.eastcoderbank.userservice.manualRegistration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/manualRegister")
@AllArgsConstructor
public class manualRegistrationController {

    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}
