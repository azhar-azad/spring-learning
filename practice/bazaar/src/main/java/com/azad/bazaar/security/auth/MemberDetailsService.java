package com.azad.bazaar.security.auth;

import com.azad.bazaar.repos.MemberRepo;
import com.azad.bazaar.security.entities.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepo memberRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberEntity memberEntity = memberRepo.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Member not found with username: " + username));

        return new MemberDetails(memberEntity);
    }
}
