package com.azad.jsonplaceholder.security.auth;

import com.azad.jsonplaceholder.security.entities.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class MemberDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final MemberEntity memberEntity;

    public MemberDetails(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + memberEntity.getRole().getRoleName()));
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
