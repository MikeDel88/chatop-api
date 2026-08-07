package com.project.chatop.doc;

import com.project.chatop.doc.utils.ApiErrorServer;
import com.project.chatop.doc.utils.ApiUnauthorized;
import com.project.chatop.dto.response.RentalsResponse;
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
@Operation(summary = "Récupération de la liste des locations.")
@ApiResponse(responseCode = "200", description = "Les locations ont bien été envoyées ou tableau vide.",
        content =  @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RentalsResponse.class)
        ))
@ApiUnauthorized
@ApiErrorServer
public @interface ApiRentalsResponse {}

