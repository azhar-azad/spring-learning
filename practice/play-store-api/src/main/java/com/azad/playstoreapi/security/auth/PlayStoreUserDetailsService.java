package com.azad.playstoreapi.security.auth;

import com.azad.playstoreapi.models.entities.PlayStoreUserEntity;
import com.azad.playstoreapi.repos.PlayStoreUserRepository;
import com.azad.playstoreapi.security.PlayStoreUserDetails;
import com.azad.playstoreapi.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlayStoreUserDetailsService implements UserDetailsService {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private PlayStoreUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        PlayStoreUserEntity user;

        if (securityUtils.isUsernameBasedAuth()) {
            user = repository.findByUsername(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with username: " + usernameOrEmail));
        } else if (securityUtils.isEmailBasedAuth()) {
            user = repository.findByEmail(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with email: " + usernameOrEmail));
        }
        else
            throw new RuntimeException("Unknown Authentication base configured. " +
                    "Valid authentication bases are USERNAME or EMAIL");

        return new PlayStoreUserDetails(user);
    }
}
