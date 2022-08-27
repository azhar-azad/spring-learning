package com.azad.ListShare.security.auth;

import com.azad.ListShare.models.entities.MemberEntity;
import com.azad.ListShare.repos.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberEntity memberEntity = memberRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found with username: " + username));

        return new MemberDetails(memberEntity);
    }
}
