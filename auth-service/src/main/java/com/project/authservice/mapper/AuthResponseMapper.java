package com.project.authservice.mapper;

import com.project.authservice.dto.auth.SignUpResponse;
import com.project.authservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthResponseMapper {

    public SignUpResponse getSignUpResponseByUser(final User savedUser) {
        final SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setName(String.join(" ", savedUser.getFirstName(), savedUser.getLastName()).trim());
        signUpResponse.setUserName(savedUser.getUsername());
        signUpResponse.setRegisterAt(savedUser.getCreatedAt());
        return signUpResponse;
    }
}
