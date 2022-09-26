package com.azad.authservice.security.auth;

import com.azad.authservice.models.entities.AppUserEntity;
import com.azad.authservice.repos.AppUserRepository;
import com.azad.authservice.security.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUserEntity user = repository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found with email: " + email));

        return new AppUserDetails(user);
    }
}
