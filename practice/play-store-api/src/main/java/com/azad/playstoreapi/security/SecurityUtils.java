package com.azad.playstoreapi.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    @Value("${auth_base}")
    private String authBase;

    public String getAuthBase() {
        return authBase;
    }

    public boolean isUsernameBasedAuth() {
        return authBase.equalsIgnoreCase("USERNAME");
    }

    public boolean isEmailBasedAuth() {
        return authBase.equalsIgnoreCase("EMAIL");
    }
}
