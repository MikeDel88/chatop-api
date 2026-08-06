package com.project.chatop.port.repository;

import com.project.chatop.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long>  {
    Optional<Rental> findRentalById(Long id);
}
