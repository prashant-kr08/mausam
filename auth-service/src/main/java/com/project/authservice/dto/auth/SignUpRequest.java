package com.project.authservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"username",
		"password",
		"email",
		"genderCode"})
@Data
public class SignUpRequest {
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	@Email
	private String email;
	private int genderCode;
}
