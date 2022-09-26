package com.azad.userauthservice.security.auth;

import com.azad.userauthservice.models.entities.AppUserEntity;
import com.azad.userauthservice.repos.AppUserRepository;
import com.azad.userauthservice.security.AppUserDetails;
import com.azad.userauthservice.security.SecurityUtils;
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
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        AppUserEntity user;

        if (securityUtils.isUsernameBasedAuth()) {
            user = userRepository.findByUsername(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with username: " + usernameOrEmail));
        } else if (securityUtils.isEmailBasedAuth()) {
            user = userRepository.findByEmail(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with email: " + usernameOrEmail));
        }
        else
            throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME and EMAIL");

        return new AppUserDetails(user);
    }
}
