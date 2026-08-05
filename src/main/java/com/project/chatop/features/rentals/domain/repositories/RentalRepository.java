package com.project.chatop.features.rentals.domain.repositories;

import com.project.chatop.features.rentals.domain.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long>  {
    Optional<Rental> findRentalById(Long id);
}
