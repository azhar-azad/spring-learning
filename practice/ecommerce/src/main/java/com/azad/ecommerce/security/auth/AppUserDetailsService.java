package com.azad.ecommerce.security.auth;

import com.azad.ecommerce.models.entities.UserEntity;
import com.azad.ecommerce.repos.UserRepository;
import com.azad.ecommerce.security.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Value("${authentication_base}")
    private String authenticationBase;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        UserEntity user;

        if (authenticationBase.equalsIgnoreCase("USERNAME")) {
            user = userRepository.findByUsername(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with username: " + usernameOrEmail));
        } else if (authenticationBase.equalsIgnoreCase("EMAIL")) {
            user = userRepository.findByEmail(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with email: " + usernameOrEmail));
        } else {
            throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME or EMAIL");
        }

        return new AppUserDetails(user);
    }
}
