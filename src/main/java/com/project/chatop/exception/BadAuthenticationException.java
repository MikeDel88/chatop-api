package com.project.chatop.exception;

public class BadAuthenticationException extends RuntimeException {

    public BadAuthenticationException(String message) {
        super(message);
    }
}