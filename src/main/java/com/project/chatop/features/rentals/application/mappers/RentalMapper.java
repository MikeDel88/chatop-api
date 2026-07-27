package com.project.chatop.features.rentals.application.mappers;

import com.project.chatop.features.rentals.domain.entities.Rental;
import com.project.chatop.features.rentals.web.dtos.RentalRequest;
import com.project.chatop.features.rentals.web.dtos.RentalResponse;
import com.project.chatop.features.users.domain.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RentalMapper {

    public Rental toRental(Long rentalId, RentalRequest rentalRequest, String pictureUrl, User owner) {
        if (rentalRequest == null) {
            return null;
        }
        return new Rental(
                rentalId,
                rentalRequest.name(),
                rentalRequest.surface(),
                rentalRequest.price(),
                pictureUrl,
                rentalRequest.description(),
                owner,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public RentalResponse toRentalResponse(Rental rental) {
        if(rental == null) {
            return null;
        }

        String europeanDatePattern = "yyyy/MM/dd";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);

        return new RentalResponse(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                rental.getOwner().getId(),
                europeanDateFormatter.format(rental.getCreatedAt()),
                europeanDateFormatter.format(rental.getUpdatedAt())
        );
    }
}
