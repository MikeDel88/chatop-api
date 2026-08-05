package com.project.chatop.features.rentals.application.services;

import com.project.chatop.features.rentals.domain.entities.Rental;
import com.project.chatop.features.rentals.web.dtos.RentalRequest;

import java.util.List;


public interface RentalService {
    List<Rental> getAll();
    Rental getById(Long id);
    Rental create(RentalRequest rentalRequest, Long userId);
    Rental update(RentalRequest rentalRequest, Long rentalId);
}

