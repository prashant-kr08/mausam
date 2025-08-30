package com.project.mausam.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.project.mausam.api.dto.auth.SignUpRequest;
import com.project.mausam.api.dto.auth.SignUpResponse;
import com.project.mausam.entity.User;
import com.project.mausam.enums.UserRole;
import com.project.mausam.mapper.MausamRequestMapper;
import com.project.mausam.mapper.MausamResponseMapper;
import com.project.mausam.repository.AuthRepository;

@Service
public class AuthService {
	
	private final AuthRepository authRepository;
	private final MausamRequestMapper mausamRequestMapper;
	private final MausamResponseMapper mausamResponseMapper;
	
	public AuthService(AuthRepository authRepository, MausamRequestMapper mausamRequestMapper, MausamResponseMapper mausamResponseMapper) {
		this.authRepository = authRepository;
		this.mausamRequestMapper = mausamRequestMapper;
		this.mausamResponseMapper = mausamResponseMapper;
	}

	public SignUpResponse registerUser(final SignUpRequest signUpRequest) {
		final User userToRegister = mausamRequestMapper.getUserBySignUpRequest(signUpRequest);
		userToRegister.setRole(UserRole.USER);
		userToRegister.setCreatedAt(LocalDateTime.now());
		final User savedUser = authRepository.save(userToRegister);
		return mausamResponseMapper.getSignUpResponseByUser(savedUser);
	}

}
