package com.azad.todolist.exceptions;

public class ResourceCreationFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ResourceCreationFailedException(String message) {
		super(message);
	}
}
