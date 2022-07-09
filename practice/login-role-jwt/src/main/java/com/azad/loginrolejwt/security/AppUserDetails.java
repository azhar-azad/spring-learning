package com.azad.loginrolejwt.security;

import com.azad.loginrolejwt.entities.Authority;
import com.azad.loginrolejwt.entities.Role;
import com.azad.loginrolejwt.entities.User;
import com.azad.loginrolejwt.utils.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class AppUserDetails implements UserDetails {

    private final User user;

    public AppUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (Constants.ROLE_BASED_AUTH) {

            return user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                    .collect(Collectors.toSet());
        }

        else if (Constants.AUTHORITY_BASED_AUTH) {

            Set<Authority> authorities = new HashSet<>();
            for (Role role: user.getRoles()) {
                authorities.addAll(role.getAuthorities());
            }

            return authorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .collect(Collectors.toSet());
        }

        else {
            throw new RuntimeException("No Authentication type provided. Choose between role based or authority based authentication");
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
