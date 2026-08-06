package com.project.chatop.exception;

public class RentalNotUpdatedException extends RuntimeException {
    public RentalNotUpdatedException() {
        super("Rental non mis à jour");
    }
}
