package com.project.chatop.features.rentals.web.exceptions;

public class RentalNotUpdatedException extends RuntimeException {
    public RentalNotUpdatedException() {
        super("Rental non mis à jour");
    }
}
