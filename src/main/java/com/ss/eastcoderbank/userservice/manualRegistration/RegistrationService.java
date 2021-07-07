package com.ss.eastcoderbank.userservice.manualRegistration;

import com.ss.eastcoderbank.userservice.model.Address;
import com.ss.eastcoderbank.userservice.model.Credential;
import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    @Autowired
    private final UserService userService;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final EmailValidator emailValidator;

    public User manualRegister(RegistrationRequest request) throws Exception {

        boolean isValidEmail = emailValidator.test(request.getEmail());

        // checks if email is valid
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        User user = registrationToUser(request);
        //        sets user's address
        Address address = new Address();
        address.setStreetAddress(request.getAddress().getStreetAddress());
        address.setCity(request.getAddress().getCity());
        address.setState(request.getAddress().getState());
        address.setZip(request.getAddress().getZip());
//        sets user's credential
        Credential credential = new Credential();
        credential.setUsername(request.getCredential().getUsername());
        credential.setPassword(request.getCredential().getPassword());


        user.setAddress(address);
        user.setCredential(credential);

        return userService.manuallyCreateUser(user);

    }
    private User registrationToUser(RegistrationRequest request) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(request, User.class);
    }

}
