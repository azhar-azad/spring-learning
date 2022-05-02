package com.azad.todolist.exceptions;

public class UnauthorizedLoginAttemptException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnauthorizedLoginAttemptException(String message) {
		super(message);
	}
}
