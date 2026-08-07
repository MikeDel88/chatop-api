package com.project.chatop.mapper;

import com.project.chatop.entity.Rental;
import com.project.chatop.dto.request.RentalRequest;
import com.project.chatop.dto.response.RentalResponse;
import com.project.chatop.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;

@Log4j2
@Component
public class RentalMapper {

    public Rental toCreateRental(RentalRequest rentalRequest, String pictureUrl, User owner) {
        log.info("toCreateRental : {}", rentalRequest);
        log.debug("toCreateRental : {}", pictureUrl);
        log.debug("toCreateRental : {}", owner);

        Rental rental = new Rental();
        rental.setOwner(owner);
        rental.setPrice(rentalRequest.price());
        rental.setSurface(rentalRequest.surface());
        rental.setPicture(pictureUrl);
        rental.setName(rentalRequest.name());
        rental.setDescription(rentalRequest.description());
        log.debug("toCreateRental : {}", rental);

        return rental;
    }


    public Rental toUpdateRental(Rental rental, RentalRequest rentalRequest) {
        log.info("toUpdateRental : {}", rentalRequest);
        log.debug("toUpdateRental : {}", rental);

        rental.setPrice(rentalRequest.price());
        rental.setSurface(rentalRequest.surface());
        rental.setName(rentalRequest.name());
        rental.setDescription(rentalRequest.description());

        log.debug("toUpdateRental : {}", rental);
        return rental;
    }

    public RentalResponse toRentalResponse(Rental rental) {
        log.info("toRentalResponse : {}", rental);

        String europeanDatePattern = "yyyy/MM/dd";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);

        RentalResponse rentalResponse = new RentalResponse(
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
        log.debug("toRentalResponse : {}", rentalResponse);
        return rentalResponse;
    }
}
