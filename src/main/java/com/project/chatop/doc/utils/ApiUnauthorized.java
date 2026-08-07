package com.project.chatop.doc.utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Erreur 401 non authorisé à accéder à l'api.
 */
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
@Operation(summary = "Authentification invalide")
@ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema()
        ))
public @interface ApiUnauthorized { }
