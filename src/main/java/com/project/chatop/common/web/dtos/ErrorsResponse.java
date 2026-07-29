package com.project.chatop.common.web.dtos;

import java.util.List;

public record ErrorsResponse(
        List<ErrorResponse> errors
) {}
