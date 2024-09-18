package com.azad.tennis_score_api.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long resourceId) {
        super("Resource not found with id: " + resourceId);
    }

    public ResourceNotFoundException(Long resourceId, String message) {
        super(message + " with id: " + resourceId);
    }
}
