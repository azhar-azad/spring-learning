package com.azad.loginrolejwt.admin;

import com.azad.loginrolejwt.entities.Authority;
import com.azad.loginrolejwt.entities.Role;
import com.azad.loginrolejwt.repos.AuthorityRepo;
import com.azad.loginrolejwt.repos.RoleRepo;
import com.azad.loginrolejwt.requests.RoleRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private ModelMapper modelMapper;

    private final RoleRepo roleRepo;
    private final AuthorityRepo authorityRepo;

    @Autowired
    public AdminService(RoleRepo roleRepo, AuthorityRepo authorityRepo) {
        this.roleRepo = roleRepo;
        this.authorityRepo = authorityRepo;
    }

    public Role saveRole(RoleRequest request) {

        Set<String> authoritiesFromReq = request.getAuthorities();

        Set<Authority> authoritiesFromDb = authoritiesFromReq.stream()
                .map(authority -> authorityRepo.findByName(authority.toUpperCase()).orElseThrow(
                        () -> new RuntimeException("Authority not found with name" + authority)))
                .collect(Collectors.toSet());

        if (authoritiesFromDb.size() < 1) {
            throw new RuntimeException("Size check. Authority not found");
        }

        Role role = modelMapper.map(request, Role.class);
        role.setName(request.getName().toUpperCase());
        role.setAuthorities(authoritiesFromDb);

        return roleRepo.save(role);
    }

    public Authority saveAuthority(Authority request) {

        request.setName(request.getName().toUpperCase());
        return authorityRepo.save(request);
    }
}
