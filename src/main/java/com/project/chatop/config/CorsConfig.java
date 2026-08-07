package com.project.chatop.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Log4j2
@Configuration
public class CorsConfig {

    @Value("#{'${app.api.domains_authorization}'.split(',')}")
    private List<String> domains;

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        log.info("CorsConfigurationSource");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Images : Empêche d'aller chercher les images depuis un fetch côté front.
        CorsConfiguration imagesConfig = new CorsConfiguration();
        imagesConfig.setAllowedOrigins(domains);
        imagesConfig.setAllowedMethods(List.of("GET", "HEAD"));
        source.registerCorsConfiguration("/images/**", imagesConfig);

        // API interne : uniquement depuis ton frontend
        CorsConfiguration apiConfig = new CorsConfiguration();
        apiConfig.setAllowedOrigins(domains);
        apiConfig.setAllowedMethods(List.of("GET", "POST", "PUT"));
        apiConfig.setAllowCredentials(true);
        source.registerCorsConfiguration("/api/**", apiConfig);

        return source;
    }
}
