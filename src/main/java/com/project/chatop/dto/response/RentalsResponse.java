package com.project.chatop.dto.response;

import java.util.List;

/**
 * Renvoi une liste de RentalReponse au client.
 * @param rentals
 */
public record RentalsResponse(
        List<RentalResponse> rentals
) { }
