package com.azad.onlinecourse.common.exception;

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