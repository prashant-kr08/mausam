package com.project.authservice.mapper;

import com.project.authservice.dto.auth.SignUpRequest;
import com.project.authservice.entity.User;
import com.project.authservice.enums.Gender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthRequestMapper {

    private final PasswordEncoder passwordEncoder;

    public AuthRequestMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserBySignUpRequest(final SignUpRequest signUpRequest) {
        final User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setGender(Gender.getGenderByCode(signUpRequest.getGenderCode()));
        return user;
    }

}

