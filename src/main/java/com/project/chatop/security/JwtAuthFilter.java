package com.project.chatop.security;

import com.project.chatop.port.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtre du JwtAuth.
 * On récupère le "Authorization" dans le header de la requête.
 * On regarde si on a bien un "Bearer " dans le header.
 * Si c'est le cas, on valide le token avec le JwtService.
 * Si le token est valide, on récupère l'id de l'utilisateur et on le met dans le contexte de sécurité.
 * Cela permet d'avoir l'utilisateur connecté dans le contexte de sécurité pour les autres filtres et les contrôleurs.
 * Le filtre est appliqué à toutes les requêtes sauf celles qui sont autorisées dans le SecurityConfig ("/api/auth/register", "/api/auth/login", "/images/**").
 */
@Log4j2
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.info("doFilterInternal {}", request.getRequestURI());
        log.debug("doFilterInternal {}", request.getHeader("Authorization"));

        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            if(jwtService.validateAccessToken(authHeader)) {
                Long userId = Long.valueOf(jwtService.getUserIdFromToken(authHeader));
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());

                log.debug("doFilterInternal {}", auth);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
