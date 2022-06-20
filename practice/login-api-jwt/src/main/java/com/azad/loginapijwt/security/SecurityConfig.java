package com.azad.loginapijwt.security;

import com.azad.loginapijwt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * This class will put all the security logic altogether and configure the security of the app.
 *
 *      The "auth" route requests are granted access by all (This is quite obvious
 *      because the user need to have access to the login and register routes).
 *
 *      The "users" route requests can only be accessed by authenticated users with
 *      the role of "USER" which is set in the AppUserDetailsService.
 *
 *      UserDetailsService is configured with the custom AppUserDetailsService bean.
 *
 *      Server is configured to reject the request as unauthorized when entry point is
 *      reached. If this point is reached it means that the current request requires
 *      authentication and no JWT token was found attached to the Authorization
 *      header of the current request.
 *
 *      The JWTFilter is added to the filter chain in order to process incoming requests.
 *
 *      A bean to get the password encoder is created.
 *
 *      Exposing the bean of the authentication manager which will be used to run
 *      the authentication process in the AuthController.
 * */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserRepo userRepo;

    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable() // Disabling csrf
                .httpBasic().disable() // Disabling http basic
                .cors() // Enabling cors
                .and()
                .authorizeHttpRequests() // Authorizing incoming requests
                    .antMatchers("/api/auth/**").permitAll() // Allows auth request to be made public without authentication of any sort
                    .antMatchers("/api/users/**").hasRole("USER") // Allows only users with the "USER role to make requests to the user routes
                .and()
                .userDetailsService(userDetailsService) // Setting the user details service to the custom implementation
                .exceptionHandling()
                    .authenticationEntryPoint(
                            // Rejecting request as unauthorized when entry point is reached.
                            // If this point is reached it means that the current request requires authentication
                            // and no JWT token was found attached to the Authorization header of the current request.
                            (request, response, authException) ->
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                    )
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Setting Session to be stateless

        // Adding the JWT filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // Creating a bean for the password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Exposing the bean of the authentication manager which will be used to run the authentication process.
    // It will be used in auth routes controller.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
