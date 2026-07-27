package com.project.chatop.features.rentals.domain.repositories;

import com.project.chatop.features.rentals.domain.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long>  {
    Rental findRentalById(Long id);
}
