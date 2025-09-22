package com.project.authservice.controller;

import com.project.authservice.dto.auth.LoginRequest;
import com.project.authservice.dto.auth.LoginResponse;
import com.project.authservice.dto.auth.SignUpRequest;
import com.project.authservice.dto.auth.SignUpResponse;
import com.project.authservice.enums.UserRole;
import com.project.authservice.service.AuthService;
import com.project.authservice.shared.api.dto.mausam.MausamApiResponse;
import com.project.authservice.utility.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mausam")
public class AuthController {
	
	private final AuthService authService;
	private final AuthenticationManager authenticationManager;
	private final JWTUtil jwtUtil;
	
	public AuthController(AuthService authService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authService = authService;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/user/signup")
	public ResponseEntity<?> registerUser(@RequestBody final SignUpRequest signUpRequest){
		final SignUpResponse signUpResponse = authService.processRegistration(signUpRequest, UserRole.USER);
		return ResponseEntity.ok(new MausamApiResponse<>(true, signUpResponse, null));
	}
	
	@PostMapping("/admin/register/user")
	@PreAuthorize("hasAuthority('REGISTER')")
	public ResponseEntity<?> adminRegisterUser(@RequestBody final SignUpRequest signUpRequest){
		final SignUpResponse signUpResponse = authService.processRegistration(signUpRequest, UserRole.USER);
		return ResponseEntity.ok(new MausamApiResponse<>(true, signUpResponse, null));
	}
	
	@PostMapping("/admin/register/admin")
	@PreAuthorize("hasAuthority('REGISTER')")
	public ResponseEntity<?> adminRegisterAdmin(@RequestBody final SignUpRequest signUpRequest){
		final SignUpResponse signUpResponse = authService.processRegistration(signUpRequest, UserRole.ADMIN);
		return ResponseEntity.ok(new MausamApiResponse<>(true, signUpResponse, null));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody final LoginRequest loginRequest){
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		final String token = jwtUtil.generateToken(loginRequest.getUsername());
		final LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(token);
		loginResponse.setExpiryInMinutes(10);
		return ResponseEntity.ok(new MausamApiResponse<>(true, loginResponse, null));
	}
}
