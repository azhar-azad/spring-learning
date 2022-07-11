package com.azad.onlineed.admin.services;

import com.azad.onlineed.models.dtos.RoleDto;
import com.azad.onlineed.repos.AuthorityRepo;
import com.azad.onlineed.repos.RoleRepo;
import com.azad.onlineed.security.entities.AuthorityEntity;
import com.azad.onlineed.security.entities.RoleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthorityRepo authorityRepo;

    private final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public RoleDto create(RoleDto roleDto) {

        RoleEntity roleEntity = modelMapper.map(roleDto, RoleEntity.class);
        roleEntity.setRoleName(roleDto.getRoleName().toUpperCase());
        roleEntity.setAuthorities(getAuthoritiesFromRequest(roleDto));

        return modelMapper.map(roleRepo.save(roleEntity), RoleDto.class);
    }

    public RoleDto getById(Integer id) {

        RoleEntity roleEntity = roleRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Role not found with id " + id));

        return modelMapper.map(roleEntity, RoleDto.class);
    }

    public RoleDto updateById(Integer id, RoleDto updatedRoleDto) {

        RoleEntity roleEntity = roleRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Role not found with id " + id));

        if (updatedRoleDto.getRoleName() != null)
            roleEntity.setRoleName(updatedRoleDto.getRoleName());
        if (updatedRoleDto.getAuthorityNames() != null) {
            roleEntity.setAuthorities(getAuthoritiesFromRequest(updatedRoleDto));
        }

        return modelMapper.map(roleRepo.save(roleEntity), RoleDto.class);
    }

    private Set<AuthorityEntity> getAuthoritiesFromRequest(RoleDto roleDto) {
        Set<String> authoritiesFromReq = roleDto.getAuthorityNames();

        return authoritiesFromReq.stream()
                .map(authorityName -> authorityRepo.findByAuthorityName(authorityName.toUpperCase()).orElseThrow(
                        () -> new RuntimeException("Authority not found with name " + authorityName)))
                .collect(Collectors.toSet());
    }

    public void deleteById(Integer id) {

        RoleEntity roleEntity = roleRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Role not found with id " + id));

        roleRepo.delete(roleEntity);
    }
}
