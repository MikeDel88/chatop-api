package com.project.chatop.features.rentals.web.controllers;

import com.project.chatop.common.web.dtos.ConfirmResponse;
import com.project.chatop.features.rentals.application.services.RentalService;
import com.project.chatop.features.rentals.web.dtos.RentalRequest;
import com.project.chatop.features.rentals.web.dtos.RentalResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.core.io.Resource;
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

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<RentalResponse>> getAll() {
        return  ResponseEntity.ok(this.rentalService.getAll());
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ConfirmResponse> create(
        @AuthenticationPrincipal Long userId,
        @Valid @ModelAttribute RentalRequest rentalRequest
    ) {
        ConfirmResponse confirmResponse = rentalService.create(rentalRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmResponse);
    }

    @PutMapping(path = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ConfirmResponse> update(
        @AuthenticationPrincipal Long userId,
        @Valid @ModelAttribute RentalRequest rentalRequest,
        @Valid @Positive @NotNull @PathVariable Long id
    ) {
        ConfirmResponse confirmResponse = rentalService.update(rentalRequest, userId, id);
        return ResponseEntity.status(HttpStatus.OK).body(confirmResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<RentalResponse> getById(@Valid @Positive @NotNull @PathVariable Long id) {
        return ResponseEntity.ok(this.rentalService.getById(id));
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@NotBlank @PathVariable String filename) {
        return this.rentalService.getPicture(filename);
    }

}
