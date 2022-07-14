package com.azad.bazaar.security.auth;

import com.azad.bazaar.security.entities.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class MemberDetails implements UserDetails {

    private final MemberEntity memberEntity;

    public MemberDetails(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return memberEntity.getRoles().stream()
                .map(roleEntity -> new SimpleGrantedAuthority("ROLE_" + roleEntity.getRoleName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return memberEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return memberEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !memberEntity.isExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !memberEntity.isLocked();
    }

    @Override
    public boolean isEnabled() {
        return memberEntity.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
