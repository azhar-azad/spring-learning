package com.azad.authenticationservice.security.auth;

import com.azad.authenticationservice.models.entities.AppUserEntity;
import com.azad.authenticationservice.repositories.AppUserRepository;
import com.azad.authenticationservice.security.SecurityUtils;
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

        AppUserEntity user = securityUtils.getUserByAuthBase(usernameOrEmail);

        return new AppUserDetails(user);
    }
}
