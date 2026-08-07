package com.project.chatop.dto.response;

import java.util.List;

/**
 * Permet de renvoyer une liste d'erreur lors d'invalidation du body.
 * @param errors
 */
public record ErrorsResponse(
        List<ErrorResponse> errors
) {}
