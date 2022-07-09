package com.azad.loginrolejwt.security.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.azad.loginrolejwt.auth.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The JWTFilter will run for each request by implementing the OncePerRequestFilter interface
 * and check if a Bearer token is present in the Authorization header.
 *
 * If a token is present, the token will be verified and the authentication data will set
 * for the user for that request by setting the authentication property of the
 * SecurityContext using the SecurityContextHolder.
 *
 * This is where our JWT comes in to action and makes sure that the user is authenticated
 * and can access protected resources that require his/her to be logged in/authenticated.
 * */

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extracting the "Authorization" header
        String authHeader = request.getHeader("Authorization");

        // Checking if the header contains a Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Extract JWT
            String jwt = authHeader.substring("Bearer ".length());

            if (jwt == null || jwt.length() == 0) {
                // Invalid JWT
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    // Verify token and extract email
                    String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);

                    // Fetch User Details
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    // Create Authentication Token
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());

                    // Setting the authentication on the Security Context using the created token
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException ex) {
                    // Failed to verify JWT
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        // Continuing the execution of the filter chain
        filterChain.doFilter(request, response);
    }
}
