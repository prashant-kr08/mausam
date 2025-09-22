package com.project.mausamservice.utility.errorhandling;

public class InvalidUnitsException extends RuntimeException {
	public InvalidUnitsException() {
		super("Invalid Units code.");
	}
}
