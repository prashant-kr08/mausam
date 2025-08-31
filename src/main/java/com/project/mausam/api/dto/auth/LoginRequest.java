package com.project.mausam.api.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"username", "password"})
@Data
public class LoginRequest {
	private String username;
	private String password;

}
