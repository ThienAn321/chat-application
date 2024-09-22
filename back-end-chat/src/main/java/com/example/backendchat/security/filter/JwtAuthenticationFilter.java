package com.example.backendchat.security.filter;

import com.example.backendchat.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Filter for JWT authentication.
 * This filter validates the JWT token provided in the Authorization header of the request.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService customUserDetailsService;

    /**
     * Validates the JWT token and sets the authentication context.
     *
     * @param request       The HTTP request
     * @param response      The HTTP response
     * @param filterChain   The filter chain
     * @throws ServletException If an error occurs during filtering
     * @throws IOException      If an I/O error occurs during filtering
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            // Extract the JWT token from the header
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtTokenProvider.extractUsername(jwt);
            // Retrieve the current authentication context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // If user email is present and no authentication is set
            if (userEmail != null && authentication == null) {
                // Get user details
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
                // Validate the JWT token and check if it is not a refresh token
                if (jwtTokenProvider.isTokenValid(jwt, userDetails) && !jwtTokenProvider.isRefreshToken(jwt)) {
                    // Create an authentication token and set it in the security context
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            // Handle exceptions by setting HTTP status to 401 and resolving the exception
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
