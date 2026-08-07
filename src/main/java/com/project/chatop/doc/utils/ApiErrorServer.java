package com.project.chatop.doc.utils;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Erreur 500, problème serveur
 */
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
@ApiResponse(responseCode = "500", description = "Problème de réponse du serveur",
        content = @Content(mediaType = "application/json"))
public @interface ApiErrorServer {}
