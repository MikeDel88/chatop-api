package com.project.chatop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = Paths.get(uploadDir).toAbsolutePath().normalize().toString();
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + path + "/")
                .setCachePeriod(3600);
    }

    /**
     * Protège l'accès aux images depuis du JS côté front via fetch()
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/images/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "HEAD")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
