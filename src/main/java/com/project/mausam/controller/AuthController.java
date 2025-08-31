package com.project.mausam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.mausam.api.dto.auth.LoginRequest;
import com.project.mausam.api.dto.auth.LoginResponse;
import com.project.mausam.api.dto.auth.SignUpRequest;
import com.project.mausam.api.dto.auth.SignUpResponse;
import com.project.mausam.api.dto.getcitymausam.MausamApiResponse;
import com.project.mausam.service.AuthService;

@RestController
@RequestMapping("/api/mausam")
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/user/signup")
	public ResponseEntity<?> registerUser(@RequestBody final SignUpRequest signUpRequest){
		final SignUpResponse signUpResponse = authService.registerUser(signUpRequest);
		return ResponseEntity.ok(new MausamApiResponse<>(true, signUpResponse, null));
	}
	
	@PostMapping("/admin/register/user")
	public ResponseEntity<?> adminRegisterUser(@RequestBody final SignUpRequest signUpRequest){
		final SignUpResponse signUpResponse = authService.adminRegisterUser(signUpRequest);
		return ResponseEntity.ok(new MausamApiResponse<>(true, signUpResponse, null));
	}
	
	@PostMapping("/admin/register/admin")
	public ResponseEntity<?> adminRegisterAdmin(@RequestBody final SignUpRequest signUpRequest){
		final SignUpResponse signUpResponse = authService.adminRegisterUser(signUpRequest);
		return ResponseEntity.ok(new MausamApiResponse<>(true, signUpResponse, null));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody final LoginRequest loginRequest){
//		final LoginResponse loginResponse = authService.login(loginRequest);
		final LoginResponse loginResponse = new LoginResponse();
		loginResponse.setExpiryInMinutes(10);
		loginResponse.setToken("hello test");
		return ResponseEntity.ok(new MausamApiResponse<>(true, loginResponse, null));
	}
}
