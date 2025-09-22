package com.project.authservice.shared.api.dto.mausamerror;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MausamErrorResponse {
//	private String code;
	private LocalDateTime timeStamp;
	private String message;
	private String description;
	
}
