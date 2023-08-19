package com.azad.taskapiwithauth.commons.exceptions;

public class ResourceNotAuthorizedException extends RuntimeException {

    public ResourceNotAuthorizedException(String message) {
        super(message);
    }
}
