package com.azad.tacocloud.tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/***
 * This configuration class will be used to configure the security for this project.
 */
@Configuration
public class SecurityConfig {

    /***
     * The following method declares a PasswordEncoder bean, which we'll use both when creating new users and when
     * authenticating users at login. In this case, we're using BCryptPasswordEncoder, one of the handful of password
     * encoders provided by Spring Security.
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
