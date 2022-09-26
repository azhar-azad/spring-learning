package com.azad.authservice.security.auth;

import com.azad.authservice.models.entities.AppUserEntity;
import com.azad.authservice.repos.AppUserRepository;
import com.azad.authservice.security.AppUserDetails;
import com.azad.authservice.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private AppUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        AppUserEntity user;

        if (securityUtils.isUsernameBasedAuth()) {
            user = repository.findByUsername(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with username: " + usernameOrEmail));
        } else if (securityUtils.isEmailBasedAuth()) {
            user = repository.findByEmail(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with email: " + usernameOrEmail));
        }
        else
            throw new RuntimeException("Unknown Authentication base configured. Valid authentication bases are USERNAME or EMAIL");

        return new AppUserDetails(user);
    }
}
