package com.project.mausam.utility.errorhandling;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.project.mausam.api.dto.getcitymausam.MausamApiResponse;

import jakarta.persistence.EntityNotFoundException;

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
			ResponseEntity<?> errorResponse = getErrorResponse(HttpStatus.BAD_REQUEST, "City not found. ", "Please check the city name and try again.");
			return errorResponse;
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
		logger.error("Error due to Request data is not valid.", methodArgumentNotValidException);
		return getErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error.", "Please check the request and try again.");
	}
	
	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<?> handlerMethodValidationExceptionHandler(final HandlerMethodValidationException handlerMethodValidationException) {
		logger.error("Error due to Request data is not valid.", handlerMethodValidationException);
		return getErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error.", "Please check the request and try again.");
	}
	
	@ExceptionHandler(CacheDataNotFoundException.class)
	public ResponseEntity<?> cacheDataNotFoundExceptionHandler(final CacheDataNotFoundException cacheDataNotFoundException) {
		logger.error("Error cache data not found.", cacheDataNotFoundException);
		return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, cacheDataNotFoundException.getMessage(), "TraceId either not valid or expired.");
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> constraintVoilationExceptionHandler(final DataIntegrityViolationException dataIntegrityViolationException) {
		logger.error("Error due to DB Contraint voilation.", dataIntegrityViolationException);
		return getErrorResponse(HttpStatus.BAD_REQUEST, "Data already exist." , "Data already saved into the system, kindly save the new data or fetch this one using it's id.");
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> entityNotFoundExceptionHandler(final EntityNotFoundException entityNotFoundException) {
		logger.error("Error due to DB data not found", entityNotFoundException);
		return getErrorResponse(HttpStatus.BAD_REQUEST, "Data not found in system for the input.", "Please check the request and try again.");
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<?> entityNotFoundExceptionHandler(final MissingServletRequestParameterException missingServletRequestParameterException) {
		logger.error("Error due to missing mandatory parameter in request.", missingServletRequestParameterException);
		return getErrorResponse(HttpStatus.BAD_REQUEST, "Mandatory parameter missing from request.", "One or more mandatory request parameter is missing.Please check and try angain.");
	}
	
	@ExceptionHandler(InvalidIdFormatException.class)
	public ResponseEntity<?> invalidIdFormatExceptionHandler(final InvalidIdFormatException invalidIdFormatException) {
		logger.error("Error due to missing mandatory parameter in request.", invalidIdFormatException);
		return getErrorResponse(HttpStatus.BAD_REQUEST, invalidIdFormatException.getMessage(), ".Please check the id and try angain.");
	}
	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<?> authorizationDeneidExceptionHandler(final AuthorizationDeniedException authorizationDeniedException) {
		logger.error("Error not have accesss.", authorizationDeniedException);
		return getErrorResponse(HttpStatus.FORBIDDEN, authorizationDeniedException.getMessage(), "User not allowed to access this service.");
	}

	private ResponseEntity<?> getErrorResponse(final HttpStatus status, final String message, final String details) {
		MausamErrorResponse mausamErrorResponse = new MausamErrorResponse(LocalDateTime.now(), message, details);
		return new ResponseEntity<>(new MausamApiResponse<>(false, null, mausamErrorResponse), status);
	}
}
