package com.project.chatop.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("L'authentification est invalide.");
    }
}
