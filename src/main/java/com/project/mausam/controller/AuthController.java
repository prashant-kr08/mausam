package com.project.mausam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}
