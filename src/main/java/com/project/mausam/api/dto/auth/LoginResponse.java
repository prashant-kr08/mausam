package com.project.mausam.api.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"token", "expiryInMinutes"})
@Data
public class LoginResponse {
	private String token;
	private int expiryInMinutes;
}
