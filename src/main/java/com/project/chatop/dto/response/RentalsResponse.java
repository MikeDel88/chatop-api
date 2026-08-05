package com.project.chatop.dto.response;

import java.util.List;

public record RentalsResponse(
        List<RentalResponse> rentals
) { }
