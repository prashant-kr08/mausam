package com.project.mausamservice.utility.errorhandling;

public class InvalidIdFormatException extends RuntimeException{
	public InvalidIdFormatException() {
		super("Invalid Id format. Please check.");
	}

	public InvalidIdFormatException(final String errorMsg) {
		super(errorMsg);
	}
}
