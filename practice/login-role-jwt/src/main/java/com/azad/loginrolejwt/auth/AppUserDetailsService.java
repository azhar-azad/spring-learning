package com.azad.loginrolejwt.auth;

import com.azad.loginrolejwt.entities.User;
import com.azad.loginrolejwt.repos.UserRepo;
import com.azad.loginrolejwt.security.AppUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AppUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found with email " + email));

        logger.info("loadUserByUsername :::: user found with roles -->");
        user.getRoles().forEach(role -> System.out.println(role.getName() + " "));

        return new AppUserDetails(user);
    }
}
