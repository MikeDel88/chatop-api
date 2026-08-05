package com.project.chatop.controller;

import com.project.chatop.dto.response.ConfirmResponse;
import com.project.chatop.dto.response.ErrorResponse;
import com.project.chatop.dto.response.ErrorsResponse;
import com.project.chatop.mapper.RentalMapper;
import com.project.chatop.port.service.RentalService;
import com.project.chatop.entity.Rental;
import com.project.chatop.dto.request.RentalRequest;
import com.project.chatop.dto.response.RentalResponse;
import com.project.chatop.dto.response.RentalsResponse;
import com.project.chatop.exception.RentalNotCreatedException;
import com.project.chatop.exception.RentalNotUpdatedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @Operation(summary = "Récupération de la liste des locations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Les locations ont bien été envoyées ou tableau vide.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalsResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Utilisateur non autorisé",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Problème de réponse du serveur",
                    content = @Content),
        }
    )
    @GetMapping
    public ResponseEntity<RentalsResponse> getAll() {
        List<Rental> rentals = this.rentalService.getAll();
        List<RentalResponse> rentalsReponse = rentals
                .stream()
                .map(rentalMapper::toRentalResponse)
                .toList();

        return  ResponseEntity.status(HttpStatus.OK).body(new RentalsResponse(rentalsReponse));
    }


    @Operation(summary = "Récupération d'une location par son identifiant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La location a bien été trouvée et envoyée",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Id invalide",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Location introuvable",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Utilisateur non autorisé",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Problème de réponse du serveur",
                    content = @Content),
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getById(@Positive @NotNull @PathVariable Long id) {
        Rental rental = this.rentalService.getById(id);
        return ResponseEntity.ok(rentalMapper.toRentalResponse(rental));
    }

    @Operation(summary = "Création d'une location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "La location a été créée avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConfirmResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Le Body est invalide",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Utilisateur non autorisé ou Location non créee",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Problème de réponse du serveur",
                    content = @Content),
        }
    )
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ConfirmResponse> create(
        @AuthenticationPrincipal Long userId,
        @Valid @ModelAttribute RentalRequest rentalRequest
    ) {
        if(rentalService.create(rentalRequest, userId) != null) {
            ConfirmResponse confirmResponse = new ConfirmResponse("Rental created !");
            return ResponseEntity.status(HttpStatus.CREATED).body(confirmResponse);
        } else {
            throw new RentalNotCreatedException();
        }
    }

    @Operation(summary = "Mise à jour d'une location par son identifiant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La location a été mise à jour avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConfirmResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Body invalide ou Id invalide",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Utilisateur non autorisé ou Location non mise à jour",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Problème de réponse du serveur",
                    content = @Content),
        }
    )
    @PutMapping(path = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ConfirmResponse> update(
        @Valid @ModelAttribute RentalRequest rentalRequest,
        @Positive @NotNull @PathVariable Long id
    ) {
        if(rentalService.update(rentalRequest, id) != null) {
            ConfirmResponse confirmResponse = new ConfirmResponse("Rental updated !");
            return ResponseEntity.status(HttpStatus.OK).body(confirmResponse);
        } else {
            throw new RentalNotUpdatedException();
        }
    }


}
