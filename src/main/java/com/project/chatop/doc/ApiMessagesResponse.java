package com.project.chatop.doc;

import com.project.chatop.doc.utils.ApiBodyInvalid;
import com.project.chatop.doc.utils.ApiErrorServer;
import com.project.chatop.doc.utils.ApiProblemDetail;
import com.project.chatop.doc.utils.ApiUnauthorized;
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
@Operation(summary = "Envoi de message au propriétaire de la location")
@ApiResponse(
        responseCode = "201",
        description = "Le message a été créée avec succès",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ConfirmResponse.class)
        ))
@ApiUnauthorized
@ApiProblemDetail(code = "400", summary = "Message Not Created")
@ApiBodyInvalid
@ApiProblemDetail(code = "404", summary = "Rental non trouvé")
@ApiProblemDetail(code = "404", summary = "User non trouvé")
@ApiErrorServer
public @interface ApiMessagesResponse {}

