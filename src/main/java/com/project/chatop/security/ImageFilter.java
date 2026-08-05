package com.project.chatop.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class ImageFilter extends OncePerRequestFilter {

    private static final List<String> ALLOWED_REFERERS = List.of("http://localhost");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/images/")) {
            String referer = request.getHeader("Referer");

            boolean isAllowed = referer != null &&
                    ALLOWED_REFERERS.stream().anyMatch(referer::startsWith);

            if (!isAllowed) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
