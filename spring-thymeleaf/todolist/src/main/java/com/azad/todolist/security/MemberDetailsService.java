package com.azad.todolist.security;

import com.azad.todolist.models.entities.MemberEntity;
import com.azad.todolist.repositorites.MemberRepository;
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

        MemberEntity member =  repository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found with email " + email));

        return new MemberDetails(member);
    }
}
