package com.project.chatop.doc.utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Erreur 400, une erreur en base de données.
 */
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
@Operation(summary = "Erreur survenue en base de données.")
@ApiResponse(
        responseCode = "400",
        description = "Une erreur est survenue en base de données.",
        content = @Content(
                mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                schema = @Schema(implementation = ProblemDetail.class)))
public @interface ApiErrorBD { }


