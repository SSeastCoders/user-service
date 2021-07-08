package com.ss.eastcoderbank.userservice.manualRegistration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="admin/manualRegistration")
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private RegistrationService regService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void manualRegistration(@RequestBody RegistrationRequest request) throws Exception {
        regService.manualRegister(request);
    }


}
