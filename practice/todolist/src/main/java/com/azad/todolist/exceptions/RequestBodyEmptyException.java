package com.azad.todolist.exceptions;

public class RequestBodyEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RequestBodyEmptyException(String message) {
		super(message);
	}
}
