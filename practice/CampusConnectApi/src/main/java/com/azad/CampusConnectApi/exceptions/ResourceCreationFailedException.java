package com.azad.CampusConnectApi.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResourceCreationFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ResourceCreationFailedException(String message) {
		super(message);
	}
}
