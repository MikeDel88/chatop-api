package com.project.chatop.doc.utils;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;

import java.lang.annotation.*;

/**
 * code(): définir le responseCode
 * summary(): description
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(ApiProblemDetails.class)
@ApiResponse(content = @Content(
        mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
        schema = @Schema(implementation = ProblemDetail.class)
))
public @interface ApiProblemDetail {

        @AliasFor(annotation = ApiResponse.class, attribute = "description")
        String summary() default "";

        @AliasFor(annotation = ApiResponse.class, attribute = "responseCode")
        String code() default "";
}
