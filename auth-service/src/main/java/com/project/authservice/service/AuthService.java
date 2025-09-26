package com.project.authservice.service;

import java.time.LocalDateTime;

import com.project.authservice.dto.auth.LoginRequest;
import com.project.authservice.dto.auth.LoginResponse;
import com.project.authservice.dto.auth.SignUpRequest;
import com.project.authservice.dto.auth.SignUpResponse;
import com.project.authservice.entity.User;
import com.project.authservice.enums.UserRole;
import com.project.authservice.mapper.AuthRequestMapper;
import com.project.authservice.mapper.AuthResponseMapper;
import com.project.authservice.repository.AuthRepository;
import com.project.authservice.utility.JWTUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	
	private final AuthRepository authRepository;
	private final AuthRequestMapper authRequestMapper;
	private final AuthResponseMapper authResponseMapper;
    private final JWTUtil jwtUtil;
	
	public AuthService(AuthRepository authRepository, AuthRequestMapper authRequestMapper,
                       AuthResponseMapper authResponseMapper, JWTUtil jwtUtil) {
		this.authRepository = authRepository;
		this.authRequestMapper = authRequestMapper;
		this.authResponseMapper = authResponseMapper;
        this.jwtUtil = jwtUtil;
	}

	public SignUpResponse processRegistration(final SignUpRequest signUpRequest, final UserRole role) {
		final User userToRegister = authRequestMapper.getUserBySignUpRequest(signUpRequest);
		userToRegister.setRole(role);
		userToRegister.setCreatedAt(LocalDateTime.now());
		final User savedUser = authRepository.save(userToRegister);
		return authResponseMapper.getSignUpResponseByUser(savedUser);
	}

    public LoginResponse login(LoginRequest loginRequest) {
        final User user = authRepository.findUserByUsername(loginRequest.getUsername());
        final String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
		final LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setExpiryInMinutes(10);
        return loginResponse;
    }
}
