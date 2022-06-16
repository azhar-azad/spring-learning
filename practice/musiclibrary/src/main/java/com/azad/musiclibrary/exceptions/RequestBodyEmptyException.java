package com.azad.musiclibrary.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestBodyEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RequestBodyEmptyException(String message) {
		super(message);
	}
}
