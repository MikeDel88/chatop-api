package com.project.chatop.features.rentals.web.exceptions;

public class RentalNotCreatedException extends RuntimeException {
    public RentalNotCreatedException() {
        super("Rental non créé");
    }
}
