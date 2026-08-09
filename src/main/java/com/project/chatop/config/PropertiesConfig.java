package com.project.chatop.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app")
@Validated
public record PropertiesConfig(
    @NotBlank
    String uploadDir,
    @NotBlank
    String baseUrl,
    @NotBlank
    String domainsAuthorization
) {}
