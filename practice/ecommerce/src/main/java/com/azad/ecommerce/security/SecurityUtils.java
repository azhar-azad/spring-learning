package com.azad.ecommerce.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    @Value("${authentication_base}")
    private String authBase;

    public boolean isUsernameBasedAuth() {
        return authBase.equalsIgnoreCase("USERNAME");
    }

    public boolean isEmailBasedAuth() {
        return authBase.equalsIgnoreCase("EMAIL");
    }
}
