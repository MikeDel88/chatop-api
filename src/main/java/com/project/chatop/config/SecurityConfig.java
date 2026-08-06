package com.project.chatop.config;

import com.project.chatop.security.ImageFilter;
import com.project.chatop.security.JwtAuthFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Log4j2
@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final ImageFilter imageFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, ImageFilter imageFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.imageFilter = imageFilter;
    }

    /**
     * Création du filter chain pour les requêtes Http
     * Désactivation CSRF (Formulaires et FormLogin par défaut).
     * Pour l'API en mode Stateless (pas d'était de sessions).
     * CORS pour aller chercher la config dans WebConfig.
     * Autorisation des routes.
     * Gestion des exceptions pour renvoyer 401 au lieu de 403.
     * Ajout d'un filtre pour vérifier le token et enregistrer l'authentification.
     * Ajout d'un filter pour vérifier l'origin "Referer" qui cherche à charger l'image.
     * @param httpSecurity HttpSecurity
     * @return HttpSecurity configuré
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
        log.info("Security Filter Chain");
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html",
                                        "/swagger-ui/**"
                                ).permitAll()
                                .requestMatchers("/api/auth/register").permitAll()
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/images/**").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling ( configurer ->
                    configurer
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(this.imageFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}