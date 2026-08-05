package com.project.chatop.config;

import com.project.chatop.security.ImageFilter;
import com.project.chatop.security.JwtAuthFilter;
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

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final ImageFilter imageFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, ImageFilter imageFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.imageFilter = imageFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
        return httpSecurity
                // Désactivation CSRF (Formulaires et FormLogin par défaut).
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                // Pour l'API en mode Stateless (pas d'était de sessions)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(Customizer.withDefaults())
                // Autorisations des routes.
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
                // Renvoi 401 au lieu de 403 en cas d'erreur principale.
                .exceptionHandling ( configurer ->
                    configurer
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                // Ajout d'un filtre pour vérifier le token et enregistrer l'authentification.
                .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Ajout d'un filter pour vérifier l'origin "Referer" qui cherche à charger l'image.
                .addFilterBefore(this.imageFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}