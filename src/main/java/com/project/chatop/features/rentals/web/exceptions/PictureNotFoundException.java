package com.project.chatop.features.rentals.web.exceptions;

public class PictureNotFoundException extends RuntimeException {
    public PictureNotFoundException() {
        super("L'image du rental est introuvable");
    }
}
