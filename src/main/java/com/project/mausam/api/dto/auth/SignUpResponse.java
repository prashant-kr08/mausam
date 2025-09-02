package com.project.mausam.api.dto.auth;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SignUpResponse {
	private String name;
	private String userName;
	private LocalDateTime registerAt;
}
