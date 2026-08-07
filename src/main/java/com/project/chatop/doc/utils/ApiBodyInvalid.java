package com.project.chatop.doc.utils;

import com.project.chatop.exception.BodyProblemDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Erreur 400, body invalide
 */
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Operation(summary = "Le Request body est invalide")
@ApiResponse(
        responseCode = "400",
        description = "Le body comporte des erreurs de validation",
        content = @Content(
                mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                schema = @Schema(implementation = BodyProblemDetail.class)))
public @interface ApiBodyInvalid { }


