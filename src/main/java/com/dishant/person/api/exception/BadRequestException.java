package com.dishant.person.api.exception;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -8157556188384774622L;

    public BadRequestException(String message) {
        super(message);
    }
}
