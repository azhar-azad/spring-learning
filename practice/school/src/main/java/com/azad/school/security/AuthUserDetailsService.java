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

        switch (userEntity.getRole()) {
            case "ROLE_ADMIN":
                return getUserDetails(email, userEntity.getPassword(), "ROLE_ADMIN");
            case "ROLE_STUDENT":
                return getUserDetails(email, userEntity.getPassword(), "ROLE_STUDENT");
            case "ROLE_TEACHER":
                return getUserDetails(email, userEntity.getPassword(), "ROLE_TEACHER");
            default:
                return getUserDetails(email, userEntity.getPassword(), "ROLE_USER");
        }
    }

    private UserDetails getUserDetails(String email, String password, String roleName) {
        return new org.springframework.security.core.userdetails.User(
                email,
                password,
                Collections.singletonList(new SimpleGrantedAuthority(roleName)));
    }
}
