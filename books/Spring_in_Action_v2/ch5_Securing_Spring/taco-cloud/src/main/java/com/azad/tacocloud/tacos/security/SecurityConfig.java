package com.azad.tacocloud.tacos.security;

import com.azad.tacocloud.tacos.data.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

/***
 * This configuration class will be used to configure the security for this project.
 */
@Configuration
public class SecurityConfig {

    /***
     * The following method declares a PasswordEncoder bean, which we'll use both when creating new users and when
     * authenticating users at login. In this case, we're using BCryptPasswordEncoder, one of the handful of password
     * encoders provided by Spring Security.
     * @return The BCryptPasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /***
     * This method creates an InMemoryUserDetailsManager with two users "buzz" and "woody".
     * Suppose we have only a handful of users, none of which are likely to change. In that case, it may be simple
     * enough to define those users as part of the security configuration.
     * @param encoder
     * @return
     *
     * This method is unregistered as Bean. We are going to use the one with database.
     */
//    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        List<UserDetails> usersList = new ArrayList<>();
        usersList.add(new User("buzz", encoder.encode("password"),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))));
        usersList.add(new User("woody", encoder.encode("password"),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))));
        return new InMemoryUserDetailsManager(usersList);
    }

    /***
     * The userDetailsService() method is given a UserRepository as a parameter. To create the bean, it returns a lambda
     * that takes a username parameter and uses it to call findByUsername() on the given UserRepository.
     * The loadByUsername() method has one simple rule; it must never return null. Therefore, if the call to
     * findByUsername() returns null, the lambda will throw a UsernameNotFoundException (which is defined by Spring
     * Security). Otherwise, the User that was found returned.
     * @param userRepository
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            com.azad.tacocloud.tacos.User user = userRepository.findByUsername(username);
            if (user != null)
                return user;

            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }
}
