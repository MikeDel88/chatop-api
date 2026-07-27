package com.project.chatop.features.users.web.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Utilisateur introuvable");
    }
}
