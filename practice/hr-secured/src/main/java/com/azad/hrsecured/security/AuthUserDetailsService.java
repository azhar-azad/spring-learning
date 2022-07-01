package com.azad.hrsecured.security;

import com.azad.hrsecured.models.entities.UserEntity;
import com.azad.hrsecured.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Fetch User from database
        Optional<UserEntity> optionalUser = userRepo.findByEmail(email);

        // No user found
        if (!optionalUser.isPresent())
            throw new UsernameNotFoundException("Could not find User with email: " + email);

        // Return a User Details object using the fetched User information
        UserEntity userEntity = optionalUser.get();

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
