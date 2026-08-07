package com.project.chatop.doc;

import com.project.chatop.doc.utils.*;
import com.project.chatop.dto.response.ConfirmResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD})
@Retention(RUNTIME)
@Operation(summary = "Mise à jour d'une location par son identifiant.")
@ApiResponse(
        responseCode = "200",
        description = "La location a été mise à jour avec succès",
        content =  @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ConfirmResponse.class)
        ))
@ApiUnauthorized
@ApiProblemDetail(code = "400", summary = "Method parameter 'id' invalid")
@ApiProblemDetail(code = "404", summary = "Rental not found")
@ApiBodyInvalid
@ApiErrorBD
@ApiErrorServer
public @interface ApiRentalUpdateResponse {}

