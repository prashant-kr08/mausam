package com.project.mausam.utility.errorhandling;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.project.mausam.api.dto.MausamApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	public static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(InvalidUnitsException.class)
	public ResponseEntity<?> invalidUnitsExceptionHandler(InvalidUnitsException invalidUnitsException) {
		logger.error("Error due to invalid units.", invalidUnitsException);
		MausamErrorResponse mausamErrorResponse = new MausamErrorResponse(LocalDateTime.now(), invalidUnitsException.getMessage(), "Invalid Units code provided. Please choice the value from 0,1,2");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MausamApiResponse<>(false, null, mausamErrorResponse));
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<?> httpClientErrorExceptionHandler(HttpClientErrorException httpClientErrorException) {
		logger.error("Error while hiting weather provider api .", httpClientErrorException);
		final String apiErrorMsg = httpClientErrorException.getMessage();
		logger.info(String.valueOf(httpClientErrorException.getStatusCode()));
		logger.info(apiErrorMsg);
		 if (httpClientErrorException.getStatusCode() == HttpStatus.NOT_FOUND && apiErrorMsg.toLowerCase().contains("city not found")) {
			 MausamErrorResponse mausamErrorResponse = new MausamErrorResponse(LocalDateTime.now(), "City not found.", "Please check the city name and try again.");
			return ResponseEntity.badRequest().body(new MausamApiResponse<>(false, null, mausamErrorResponse));
		}
		MausamErrorResponse mausamErrorResponse = new MausamErrorResponse(LocalDateTime.now(), "Some error occured.", "Please contact support.");
		return new ResponseEntity<>(new MausamApiResponse<>(false, null, mausamErrorResponse), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> genericExceptionHandler(Exception exception) {
		logger.error("Some Error Occured in system.", exception);
		MausamErrorResponse mausamErrorResponse = new MausamErrorResponse(LocalDateTime.now(), "Some error occured", "Please contact support.");
		return new ResponseEntity<>(new MausamApiResponse<>(false, null, mausamErrorResponse), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
