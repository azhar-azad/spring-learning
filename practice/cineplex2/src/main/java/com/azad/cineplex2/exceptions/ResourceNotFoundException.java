package com.azad.cineplex2.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String resourceName, Long id) {
		super("Resource " + resourceName + " not found with the id: " + id);
	}
	
	public ResourceNotFoundException(String resourceName, String attributeName) {
		super("Resource " + resourceName + " not found with the attribute: " + attributeName);
	}

}
