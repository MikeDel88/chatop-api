package com.project.chatop.exception;

public class RentalNotFoundException extends RuntimeException {
    public RentalNotFoundException() {
        super("Rental introuvable");
    }
}
