package com.azad.onlineed.security.auth;

import com.azad.onlineed.models.Authority;
import com.azad.onlineed.models.Role;
import com.azad.onlineed.security.entities.AuthorityEntity;
import com.azad.onlineed.security.entities.RoleEntity;
import com.azad.onlineed.security.entities.UserEntity;
import com.azad.onlineed.utils.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AppUserDetails implements UserDetails {

    private final UserEntity userEntity;

    public AppUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (Constants.ROLE_BASED_AUTH) {

            return userEntity.getRoles().stream()
                    .map(roleEntity -> new SimpleGrantedAuthority("ROLE_" + roleEntity.getRoleName()))
                    .collect(Collectors.toSet());
        }

        else if (Constants.AUTHORITY_BASED_AUTH) {

            Set<AuthorityEntity> authorityEntities = new HashSet<>();
            for (RoleEntity roleEntity: userEntity.getRoles()) {
                authorityEntities.addAll(roleEntity.getAuthorities());
            }
            return authorityEntities.stream()
                    .map(authorityEntity -> new SimpleGrantedAuthority(authorityEntity.getAuthorityName()))
                    .collect(Collectors.toSet());
        }

        throw new RuntimeException("No Authentication type provided. Choose between role based or authority based authentication");
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return userEntity.isEnabled();
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
}
