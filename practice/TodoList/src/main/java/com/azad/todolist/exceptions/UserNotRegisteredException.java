package com.azad.todolist.exceptions;

public class UserNotRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotRegisteredException(String message) {
		super(message);
	}
}
