package com.listofemployee.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import java.io.IOException;

@Component
@RequiredArgsConstructor

/**
 * This class is a Spring Security filter that validates JWT tokens and sets the user authentication context.
 * It extends `OncePerRequestFilter` to ensure that the JWT validation logic is executed only once per request.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * This method performs JWT token validation and sets the user authentication context.
     *
     * @param request  HTTP request
     * @param response HTTP response
     * @param filterChain filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(
            @NonNull
            HttpServletRequest request,
            @NonNull
            HttpServletResponse response,
            @NonNull
            FilterChain filterChain
        ) throws ServletException, IOException {
        // Extract JWT token from Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        // If there is no JWT token or it doesn't start with "Bearer ", skip filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        // Extract user email from the JWT token
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUserEmail(jwt);

        // If user email is extracted and authentication context is not set
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // we get userDeatils from database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // we check if user is valid or not
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // if user and token valid we create UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                //update auth token
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        filterChain.doFilter(request,response);
    }
}
