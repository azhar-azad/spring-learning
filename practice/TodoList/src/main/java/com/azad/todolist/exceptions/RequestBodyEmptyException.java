package com.azad.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RequestBodyEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequestBodyEmptyException(String message) {
		super(message);
	}
}
