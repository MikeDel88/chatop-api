package com.project.chatop.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Log4j2
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir}")
    private String uploadDir;

    /**
     * Permet d'accéder aux images stockées sur le serveur.
     * cache par le client 1 heure.
     * @param registry ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = Paths.get(uploadDir).toAbsolutePath().normalize().toString();
        log.debug("addResourceHandlers : {}", path);
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + path + "/")
                .setCachePeriod(3600);
    }

}
