package com.azad.loginapijwt.security;

import com.azad.loginapijwt.repository.UserRepo;
import com.azad.loginapijwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

/**
 * A UserDetailsService is used to fetch the user details of the user trying to authenticate
 * into the application. This is done in the loadUserByUsername method.
 * If no such user is found a UsernameNotFoundException is thrown.
 *
 * Behind the scene about UserDetailsService:
 *  <a href="https://medium.com/geekculture/spring-security-authentication-process-authentication-flow-behind-the-scenes-d56da63f04fa">UserDetailService</a>
 * */

@Component
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Fetch User from the DB
        Optional<User> optionalUser = userRepo.findByEmail(email);

        // No user found
        if (!optionalUser.isPresent())
            throw new UsernameNotFoundException("Could not find User with email: " + email);

        // Return a User Details object using the fetched User information
        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(
                email, // username
                user.getPassword(), // password
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // Sets the role of the found user
        );
    }
}
