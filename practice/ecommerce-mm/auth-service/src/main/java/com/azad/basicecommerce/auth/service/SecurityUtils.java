package com.azad.basicecommerce.auth.service;

import com.azad.basicecommerce.auth.models.AppUserEntity;
import com.azad.basicecommerce.auth.reposotories.AppUserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SecurityUtils {

    @Value("${auth_base}")
    private String authBase;

    @Autowired
    private AppUserRepository repository;

    public boolean isUsernameBasedAuth() {
        return authBase.equalsIgnoreCase("USERNAME");
    }

    public boolean isEmailBasedAuth() {
        return authBase.equalsIgnoreCase("EMAIL");
    }

    public AppUserEntity getUserByAuthBase(String usernameOrEmail) throws RuntimeException {
        if (isUsernameBasedAuth())
            return repository.findByUsername(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with username: " + usernameOrEmail));
        else if (isEmailBasedAuth())
            return repository.findByEmail(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with email: " + usernameOrEmail));
        else
            throw new RuntimeException("Unknown Authentication base configured. Valid auth_base values are USERNAME or EMAIL");
    }
}
