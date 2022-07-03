package com.azad.school.security;

import com.azad.school.models.entities.UserEntity;
import com.azad.school.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepo.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Could not find User with email: " + email));

        if (userEntity.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
            return new org.springframework.security.core.userdetails.User(
                    email,
                    userEntity.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        return new org.springframework.security.core.userdetails.User(
                email,
                userEntity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
