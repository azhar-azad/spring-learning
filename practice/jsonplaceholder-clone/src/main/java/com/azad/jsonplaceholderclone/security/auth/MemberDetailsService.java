package com.azad.jsonplaceholderclone.security.auth;

import com.azad.jsonplaceholderclone.repos.MemberRepository;
import com.azad.jsonplaceholderclone.security.entities.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberEntity memberEntity = memberRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found with username: " + username));

        return new MemberDetails(memberEntity);
    }
}
