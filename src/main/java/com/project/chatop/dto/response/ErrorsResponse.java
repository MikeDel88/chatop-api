package com.project.chatop.dto.response;

import java.util.List;

public record ErrorsResponse(
        List<ErrorResponse> errors
) {}
