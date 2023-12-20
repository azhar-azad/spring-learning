package com.azad.multiplex.security;

import com.azad.multiplex.model.entity.MemberEntity;
import com.azad.multiplex.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity member = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email " + email));

        return new MemberDetails(member);
    }
}
