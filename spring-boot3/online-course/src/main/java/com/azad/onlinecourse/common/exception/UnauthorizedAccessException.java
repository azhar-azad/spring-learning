package com.azad.onlinecourse.common.exception;

import lombok.Getter;

@Getter
public class UnauthorizedAccessException extends RuntimeException {

    private String validAccess;

    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(String message, String validAccess) {
        super(message);
        this.validAccess = validAccess;
    }
}

