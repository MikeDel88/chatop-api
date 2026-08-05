package com.project.chatop.features.rentals.application.mappers;

import com.project.chatop.features.rentals.domain.entities.Rental;
import com.project.chatop.features.rentals.web.dtos.RentalRequest;
import com.project.chatop.features.rentals.web.dtos.RentalResponse;
import com.project.chatop.features.users.domain.entities.User;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;

@Component
public class RentalMapper {

    public Rental toCreateRental(RentalRequest rentalRequest, String pictureUrl, User owner) {
        if (rentalRequest == null) {
            return null;
        }
        Rental rental = new Rental();
        rental.setOwner(owner);
        rental.setPrice(rentalRequest.price());
        rental.setSurface(rentalRequest.surface());
        rental.setPicture(pictureUrl);
        rental.setName(rentalRequest.name());
        rental.setDescription(rentalRequest.description());

        return rental;
    }

    public Rental toUpdateRental(Rental rental, RentalRequest rentalRequest) {
        if (rentalRequest == null || rental == null) {
            return null;
        }

        rental.setPrice(rentalRequest.price());
        rental.setSurface(rentalRequest.surface());
        rental.setName(rentalRequest.name());
        rental.setDescription(rentalRequest.description());

        return rental;
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
