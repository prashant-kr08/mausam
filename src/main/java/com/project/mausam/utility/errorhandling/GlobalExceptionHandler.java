package com.project.mausam.utility.errorhandling;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
		return getErrorResponse(HttpStatus.BAD_REQUEST, invalidUnitsException.getMessage(), "Invalid Units code provided. Please choice the value from 0,1,2");
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<?> httpClientErrorExceptionHandler(HttpClientErrorException httpClientErrorException) {
		logger.error("Error while hiting weather provider api .", httpClientErrorException);
		final String apiErrorMsg = httpClientErrorException.getMessage();
		logger.info(String.valueOf(httpClientErrorException.getStatusCode()));
		logger.info(apiErrorMsg);
		 if (httpClientErrorException.getStatusCode() == HttpStatus.NOT_FOUND && apiErrorMsg.toLowerCase().contains("city not found")) {
			return getErrorResponse(HttpStatus.BAD_REQUEST, "City not found.", "Please check the city name and try again.");
		}
		 return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Some error occured.", "Please contact support.");
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> genericExceptionHandler(final Exception exception) {
		logger.error("Some Error Occured in system.", exception);
		return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Some error occured", "Please contact support.");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValidExceptionHandler(final MethodArgumentNotValidException methodArgumentNotValidException) {
		logger.error("Some Error Occured in system.", methodArgumentNotValidException);
		return getErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error.", "Please check the request and try again.");
	}

	private ResponseEntity<?> getErrorResponse(final HttpStatus status, final String message, final String details) {
		MausamErrorResponse mausamErrorResponse = new MausamErrorResponse(LocalDateTime.now(), message, details);
		return new ResponseEntity<>(new MausamApiResponse<>(false, null, mausamErrorResponse), status);
	}
}
