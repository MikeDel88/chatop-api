package com.project.chatop.features.auth.web.exceptions;

public class BadAuthenticationException extends RuntimeException {

    public BadAuthenticationException(String message) {
        super(message);
    }
}