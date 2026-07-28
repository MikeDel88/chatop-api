package com.project.chatop.features.rentals.web.dtos;

import java.util.List;

public record RentalsResponse(
        List<RentalResponse> rentals
) { }
