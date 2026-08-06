package com.project.chatop.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Log4j2
@Configuration
public class OpenApiConfig {

    /**
     * Création de la configuration de la documentation OpenApi.
     * Ajout du titre et de la description
     * Ajout du github avec le code source du projet
     * Ajout du schéma de sécurité Bearer Jwt
     * @return OpenAPI configuration
     */
    @Bean
    public OpenAPI customOpenAPI() {
        log.info("OpenAPI Documentation");
        return new OpenAPI()
                .info(
                        new Info()
                                .title("ChaTop API")
                                .description("API REST de Location")
                                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Code source du projet")
                                .url("https://github.com/MikeDel88/ChaTop_Api")
                )
                .components(
                        new Components().addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

}
