package com.project.mausam.utility.errorhandling;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MausamErrorResponse {
//	private String code;
	private LocalDateTime timeStamp;
	private String message;
	private String description;
	
}
