package com.project.chatop.port.repository;

import com.project.chatop.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long>  {
    Optional<Rental> findRentalById(Long id);
}
