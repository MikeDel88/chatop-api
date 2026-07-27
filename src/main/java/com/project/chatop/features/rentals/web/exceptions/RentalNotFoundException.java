package com.project.chatop.features.rentals.web.exceptions;

public class RentalNotFoundException extends RuntimeException {
    public RentalNotFoundException() {
        super("Rental introuvable");
    }
}
