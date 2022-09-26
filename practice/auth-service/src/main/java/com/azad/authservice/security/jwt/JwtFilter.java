package com.azad.authservice.security.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.azad.authservice.security.auth.AppUserDetailsService;
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

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring("Bearer ".length());

            if (jwt == null || jwt.length() == 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    String email = jwtUtil.validateJwtTokenAndRetrieveClaim(jwt);

                    UserDetails userDetails = appUserDetailsService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null)
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (JWTVerificationException ex) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
