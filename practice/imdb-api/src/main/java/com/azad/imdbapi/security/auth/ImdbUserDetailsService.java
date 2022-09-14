package com.azad.imdbapi.security.auth;

import com.azad.imdbapi.entities.ImdbUserEntity;
import com.azad.imdbapi.repos.ImdbUserRepository;
import com.azad.imdbapi.security.ImdbUserDetails;
import com.azad.imdbapi.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImdbUserDetailsService implements UserDetailsService {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private ImdbUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        ImdbUserEntity user;

        if (securityUtils.isUsernameBasedAuth()) {
            user = userRepository.findByUsername(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with username: " + usernameOrEmail));
        } else if (securityUtils.isEmailBasedAuth()) {
            user = userRepository.findByEmail(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with email: " + usernameOrEmail));
        } else {
            throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME or EMAIL");
        }

        return new ImdbUserDetails(user);
    }
}
