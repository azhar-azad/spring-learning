package com.azad.onlineed.security.auth;

import com.azad.onlineed.repos.UserRepo;
import com.azad.onlineed.security.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepo.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found with email: " + email));

        return new AppUserDetails(userEntity);
    }
}
