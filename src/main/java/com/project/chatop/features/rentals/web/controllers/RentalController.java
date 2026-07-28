package com.project.chatop.features.rentals.web.controllers;

import com.project.chatop.common.web.dtos.ConfirmResponse;
import com.project.chatop.features.rentals.application.mappers.RentalMapper;
import com.project.chatop.features.rentals.application.services.RentalService;
import com.project.chatop.features.rentals.domain.entities.Rental;
import com.project.chatop.features.rentals.web.dtos.RentalRequest;
import com.project.chatop.features.rentals.web.dtos.RentalResponse;
import com.project.chatop.features.rentals.web.dtos.RentalsResponse;
import com.project.chatop.features.rentals.web.exceptions.RentalNotCreatedException;
import com.project.chatop.features.rentals.web.exceptions.RentalNotUpdatedException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final RentalMapper rentalMapper;

    public RentalController(RentalService rentalService, RentalMapper rentalMapper) {
        this.rentalService = rentalService;
        this.rentalMapper = rentalMapper;
    }

    @GetMapping
    public ResponseEntity<RentalsResponse> getAll() {
        List<Rental> rentals = this.rentalService.getAll();
        List<RentalResponse> rentalsReponse = rentals
                .stream()
                .map(rentalMapper::toRentalResponse)
                .toList();

        return  ResponseEntity.status(HttpStatus.OK).body(new RentalsResponse(rentalsReponse));
    }


    @GetMapping("{id}")
    public ResponseEntity<RentalResponse> getById(@Valid @Positive @NotNull @PathVariable Long id) {
        Rental rental = this.rentalService.getById(id);
        return ResponseEntity.ok(rentalMapper.toRentalResponse(rental));
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ConfirmResponse> create(
        @AuthenticationPrincipal Long userId,
        @Valid @ModelAttribute RentalRequest rentalRequest
    ) {
        if(rentalService.create(rentalRequest, userId) != null) {
            ConfirmResponse confirmResponse = new ConfirmResponse("Rental created !");;
            return ResponseEntity.status(HttpStatus.OK).body(confirmResponse);
        } else {
            throw new RentalNotCreatedException();
        }
    }

    @PutMapping(path = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ConfirmResponse> update(
        @Valid @ModelAttribute RentalRequest rentalRequest,
        @Valid @Positive @NotNull @PathVariable Long id
    ) {
        if(rentalService.update(rentalRequest, id) != null) {
            ConfirmResponse confirmResponse = new ConfirmResponse("Rental updated !");;
            return ResponseEntity.status(HttpStatus.OK).body(confirmResponse);
        } else {
            throw new RentalNotUpdatedException();
        }
    }


}
