package com.azad.school.exceptions;

public class InvalidPathVariableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidPathVariableException(String message) {
		super(message);
	}
}
