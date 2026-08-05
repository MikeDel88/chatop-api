package com.project.chatop.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Utilisateur introuvable");
    }
}
