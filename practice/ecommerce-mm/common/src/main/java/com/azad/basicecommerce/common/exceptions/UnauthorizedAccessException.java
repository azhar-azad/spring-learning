package com.azad.basicecommerce.common.exceptions;

public class UnauthorizedAccessException extends RuntimeException{

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
