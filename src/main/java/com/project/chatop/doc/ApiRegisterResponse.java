package com.project.chatop.doc;

import com.project.chatop.doc.utils.ApiBodyInvalid;
import com.project.chatop.doc.utils.ApiErrorBD;
import com.project.chatop.doc.utils.ApiErrorServer;
import com.project.chatop.dto.response.AuthResponse;
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
@Operation(summary = "Créer un compte utilisateur")
@ApiResponse(
        responseCode = "201",
        description = "création de l'utilisateur",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthResponse.class)
        ))
@ApiErrorBD
@ApiBodyInvalid
@ApiErrorServer
public @interface ApiRegisterResponse {}