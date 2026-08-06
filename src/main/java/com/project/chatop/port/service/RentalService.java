package com.project.chatop.port.service;

import com.project.chatop.entity.Rental;
import com.project.chatop.dto.request.RentalRequest;

import java.util.List;


public interface RentalService {
    List<Rental> getAll();
    Rental getById(Long id);
    Rental create(RentalRequest rentalRequest, Long userId);
    Rental update(RentalRequest rentalRequest, Long rentalId);
}

