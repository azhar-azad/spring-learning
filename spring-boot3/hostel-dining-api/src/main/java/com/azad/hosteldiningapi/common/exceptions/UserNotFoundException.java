package com.azad.hosteldiningapi.common.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private String userIdentifier;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, String userIdentifier) {
        super(message);
        this.userIdentifier = userIdentifier;
    }
}
