package com.azad.estatement.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvalidPathVariableException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidPathVariableException(String message) {
		super(message);
	}
}
