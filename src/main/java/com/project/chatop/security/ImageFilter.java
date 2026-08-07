package com.project.chatop.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtre les images qui arrive depuis un "src" en front.
 * On regarde les Referer pour savoir si la requête est autorisée.
 * Si le Referer n'est pas dans la liste des autorisés, on renvoie un code 403 Forbidden.
 * Cela permet d'éviter que des sites externes utilisent nos images sans autorisation.
 * Le filtre est appliqué à toutes les requêtes qui commencent par "/images/"
 */
@Log4j2
@Component
public class ImageFilter extends OncePerRequestFilter {

    @Value("#{'${app.api.domains_authorization}'.split(',')}")
    private List<String> allowedReferers;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.info("doFilterInternal {}", request.getRequestURI());
        log.debug("doFilterInternal {}", request.getHeader("Referer"));

        if (request.getRequestURI().startsWith("/images/")) {
            String referer = request.getHeader("Referer");

            boolean isAllowed = referer != null &&
                    allowedReferers.stream().anyMatch(referer::startsWith);

            if (!isAllowed) {
                log.error("Forbidden request to from referer");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
