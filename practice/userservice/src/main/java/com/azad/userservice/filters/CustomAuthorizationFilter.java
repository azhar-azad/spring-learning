package com.azad.userservice.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		if (request.getServletPath().equals("/login")) {
			// It is a login path, we don't have to do anything. The user is just trying to login
			filterChain.doFilter(request, response);
		} else {
			// Not a login route, so authorize the requests
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // key of that header is passed. i.e. "Authorization" 
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				// Do this for only "Authorization" headers which starts with "Bearer "
				try {
					String token = authorizationHeader.substring("Bearer ".length()); // remove the "Bearer " portion. We just need the token.
					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // this secret should be same that signs the algorithm.
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String username = decodedJWT.getSubject(); 
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class); // get this value from the .withClaim()
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					for (String role: roles) {
						authorities.add(new SimpleGrantedAuthority(role));
					}
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					
					filterChain.doFilter(request, response);
				} catch (Exception ex) {
					log.error("Error loggin in: {}", ex.getMessage());
					response.setHeader("error", ex.getMessage());
					response.setStatus(HttpStatus.FORBIDDEN.value());
//					response.sendError(HttpStatus.FORBIDDEN.value()); // if error is send then the next lines will not get executed
					
					Map<String, String> errors = new HashMap<>();
					errors.put("error_message", ex.getMessage());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), errors);
				}
				
			} else {
				// Do this for all other requests headers except "Authorization"
				filterChain.doFilter(request, response);
			}
		}
		
	}

}
