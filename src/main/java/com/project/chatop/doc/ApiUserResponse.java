package com.project.chatop.doc;

import com.project.chatop.doc.utils.ApiErrorBD;
import com.project.chatop.doc.utils.ApiErrorServer;
import com.project.chatop.doc.utils.ApiProblemDetail;
import com.project.chatop.doc.utils.ApiUnauthorized;
import com.project.chatop.dto.response.UserResponse;
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
@Operation(summary = "Récupération des informations du propriétaire par son identifiant pour une location.")
@ApiResponse(
        responseCode = "200",
        description = "L'utilisateur a bien été envoyé",
        content =  @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class)
        ))
@ApiUnauthorized
@ApiProblemDetail(code = "400", summary = "Method parameter 'id' invalid")
@ApiProblemDetail(code = "404", summary = "Utilisateur non trouvé")
@ApiErrorBD
@ApiErrorServer
public @interface ApiUserResponse {}

