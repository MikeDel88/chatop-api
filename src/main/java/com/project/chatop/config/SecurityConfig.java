package com.project.chatop.config;

import com.project.chatop.security.ImageFilter;
import com.project.chatop.security.JwtAccessDeniedHandler;
import com.project.chatop.security.JwtAuthenticationEntryPoint;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Log4j2
@Configuration
public class SecurityConfig {

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
     * @param jwtEntryPoint JwtAuthenticationEntryPoint
     * @param jwtAccessDeniedHandler JwtAccessDeniedHandler
     * @param imageFilter ImageFilter
     * @return HttpSecurity configuré
     */
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity,
            JwtAuthenticationEntryPoint jwtEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler,
            ImageFilter imageFilter
    ) {
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
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(jwtEntryPoint)
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .addFilterBefore(imageFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}