package com.azad.bazaar.admin;

import com.azad.bazaar.models.dtos.RoleDto;
import com.azad.bazaar.repos.RoleRepo;
import com.azad.bazaar.security.entities.RoleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private ModelMapper modelMapper;

    private final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public RoleDto create(RoleDto roleDto) {

        RoleEntity roleEntity = modelMapper.map(roleDto, RoleEntity.class);
        roleEntity.setRoleName(roleEntity.getRoleName().toUpperCase());

        return modelMapper.map(roleRepo.save(roleEntity), RoleDto.class);
    }
}
