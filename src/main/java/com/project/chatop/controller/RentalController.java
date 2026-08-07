package com.project.chatop.controller;

import com.project.chatop.doc.ApiRentalCreateResponse;
import com.project.chatop.doc.ApiRentalResponse;
import com.project.chatop.doc.ApiRentalUpdateResponse;
import com.project.chatop.doc.ApiRentalsResponse;
import com.project.chatop.dto.response.ConfirmResponse;
import com.project.chatop.mapper.RentalMapper;
import com.project.chatop.port.service.RentalService;
import com.project.chatop.entity.Rental;
import com.project.chatop.dto.request.RentalRequest;
import com.project.chatop.dto.response.RentalResponse;
import com.project.chatop.dto.response.RentalsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Log4j2
@Tag(name = "Rentals", description = "Gestion des locations")
@Validated
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final RentalMapper rentalMapper;

    public RentalController(RentalService rentalService, RentalMapper rentalMapper) {
        this.rentalService = rentalService;
        this.rentalMapper = rentalMapper;
    }

    @ApiRentalsResponse
    @GetMapping
    public RentalsResponse getAll() {
        log.info("call /getAll rentals");
        List<Rental> rentals = this.rentalService.getAll();
        List<RentalResponse> rentalsReponse = rentals
                .stream()
                .map(rentalMapper::toRentalResponse)
                .toList();

        return new RentalsResponse(rentalsReponse);
    }


    @ApiRentalResponse
    @GetMapping("/{id}")
    public RentalResponse getById(@Positive @NotNull @PathVariable Long id) {
        log.info("call /getById id {}", id);
        Rental rental = this.rentalService.getById(id);
        return rentalMapper.toRentalResponse(rental);
    }

    @ApiRentalCreateResponse
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ConfirmResponse> create(
        @AuthenticationPrincipal Jwt jwt,
        @Valid @ModelAttribute RentalRequest rentalRequest
    ) {
        log.info("call /create");
        rentalService.create(rentalRequest, Long.valueOf(Objects.requireNonNull(jwt.getSubject())));
        ConfirmResponse confirmResponse = new ConfirmResponse("Rental created !");
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmResponse);
    }

    @ApiRentalUpdateResponse
    @PutMapping(path = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ConfirmResponse update(
        @Valid @ModelAttribute RentalRequest rentalRequest,
        @Positive @NotNull @PathVariable Long id
    ) {
        log.info("call /update id {}", id);
        rentalService.update(rentalRequest, id);
        return new ConfirmResponse("Rental updated !");
    }

}
