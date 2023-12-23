package com.azad.supershop.exception;

public class MethodBlockedException extends RuntimeException {

    public MethodBlockedException() {
        super("Method is intentionally blocked");
    }
}
